package com.dev.alahlifc.al_ahlysportsclub.BigImage

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityBigImageBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mSubAlbum
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator

class BigImageActivity : LocalizationActivity() {

    private lateinit var binding: ActivityBigImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_big_image)

        val imageObj : mSubAlbum.SubAlbum = intent.getParcelableExtra("obj")


        binding.mBigImage.setProgressIndicator(ProgressPieIndicator())
        val url = Constants.baseUrl + imageObj.image
        binding.mBigImage.showImage(
            Uri.parse(url)
        )
    }

    override fun onStart() {
        super.onStart()
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroy() {
        super.onDestroy()
        BigImageViewer.imageLoader().cancelAll()
    }
}
