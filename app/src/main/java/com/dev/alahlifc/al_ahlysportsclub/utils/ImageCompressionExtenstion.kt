package com.dev.alahlifc.al_ahlysportsclub.utils

import android.media.ExifInterface
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.os.AsyncTask

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException



/**
 * Created by mahmoud_ashraf on 28,04,2019
 */

class ImageCompression(
    @field:SuppressLint("StaticFieldLeak")
    private val context: Context, private val filePath: String,
    private val imageCompressionListener: ImageCompressionListener
) : AsyncTask<String, Void, String>() {

    private//File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/Compressed");
    // Create the storage directory if it does not exist
    val filename: String
        get() {

            val mediaStorageDir = File(context.getExternalFilesDir(""), "compressed")
            if (!mediaStorageDir.exists()) {
                mediaStorageDir.mkdirs()
            }

            val mImageName = "IMG_" + System.currentTimeMillis().toString() + ".png"
            return mediaStorageDir.getAbsolutePath() + "/" + mImageName

        }

    override fun onPreExecute() {
        super.onPreExecute()
        imageCompressionListener.onStart()
    }

    override fun doInBackground(vararg strings: String): String {
        //if (strings.length == 0 || strings[0] == null)
        //return null;

        return compressImage(filePath)
    }

    override fun onPostExecute(imagePath: String) {
        // imagePath is path of new compressed image.
        imageCompressionListener.onCompressed(imagePath)
    }


    private fun compressImage(imagePath: String): String {
        var scaledBitmap: Bitmap? = null

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(imagePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        var imgRatio = actualWidth.toFloat() / actualHeight.toFloat()
        val maxRatio = maxWidth / maxHeight

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        options.inJustDecodeBounds = false
        options.inDither = false
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()

        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(bmp, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))

        bmp.recycle()

        val exif: ExifInterface
        try {
            exif = ExifInterface(imagePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            when (orientation) {
                6 -> matrix.postRotate(90F)
                3 -> matrix.postRotate(180F)
                8 -> matrix.postRotate(270F)
            }
            scaledBitmap =
                Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap!!.width, scaledBitmap.height, matrix, true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val out: FileOutputStream
        val filepath = filename
        try {
            out = FileOutputStream(filepath)

            //write the compressed bitmap at the destination specified by filename.
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return filepath
    }

    companion object {
        private val maxHeight = 280.0f //1280
        private val maxWidth = 280.0f

        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }

            return inSampleSize
        }
    }
}

 interface ImageCompressionListener {
    fun onStart()

    fun onCompressed(filePath : String)
}