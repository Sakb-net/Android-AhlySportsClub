package com.dev.alahlifc.al_ahlysportsclub.Base

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.content.res.Configuration
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.TypefaceSpan
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Base64
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_cart_confirm.view.*
import kotlinx.android.synthetic.main.dialog_enter_name.view.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.dialog_view.view.positiveBtn
import java.io.ByteArrayOutputStream


/**
 * Created by mahmoud_ashraf on 12,05,2019
 */

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}




fun Context.showDialog(@DrawableRes drawableRes: Int, resID: String, positive:(dialog: AlertDialog?) -> Unit, negative:(dialog: AlertDialog?) -> Unit){
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null)
    val alertDialog = AlertDialog.Builder(this).setView(view).setCancelable(false)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    view.dialogIcon.setImageResource(drawableRes)
    view.dialogText.text = resID
    view.positiveBtn.setOnClickListener{positive(dialog)}
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    view.negativeButton.setOnClickListener{negative(dialog)}
    dialog.show()
}

fun Context.showConnectionErrorView(onRetry: () -> Unit) : Snackbar {
    val snackbar = Snackbar
        .make(
            (this as Activity).findViewById(android.R.id.content),
           getString( R.string.snackmessage), Snackbar.LENGTH_INDEFINITE
        )


    val tv = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
    val font = Typeface.createFromAsset(this.getAssets(), Constants.FONT_BOLD)
    tv.setTypeface(font)
    //////////////////
    val tv2 = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    val font2 = Typeface.createFromAsset(this.getAssets(), Constants.FONT_REGULAR)
    tv2.setTypeface(font2)


    snackbar.view.setBackgroundColor(Color.parseColor("#80d6392e"))
    snackbar.setAction(getString(R.string.retry)) { onRetry() }
        .setActionTextColor(Color.parseColor("#ffffff"))
        .show()

    return snackbar
}


fun Context.showCartDialog( total : String, positive:(dialog: AlertDialog?) -> Unit){
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_cart_confirm, null)
    val alertDialog = AlertDialog.Builder(this).setView(view)
        .setCancelable(true)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(false)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    Util.changeViewTypeFace(this,
        Constants.FONT_REGULAR,
        view.total_info,
        view.total_price,
        view.positiveBtn)
  //  view.dialogIcon.setImageResource(drawableRes)
  // view.dialogText.text = resID
    view.total_price.text = total
    view.positiveBtn.setOnClickListener{positive(dialog)}
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
 //   view.negativeButton.setOnClickListener{negative(dialog)}
    dialog.show()
}



fun Context.showPaymentSuccessDialog(response : String, positive:(dialog: AlertDialog?) -> Unit){
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_cart_success, null)
    val alertDialog = AlertDialog.Builder(this).setView(view)
        .setCancelable(false)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(false)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    Util.changeViewTypeFace(this,
        Constants.FONT_REGULAR,
        view.total_info,
        view.positiveBtn)
    //  view.dialogIcon.setImageResource(drawableRes)
    // view.dialogText.text = resID
    view.total_info.text = response
    if (""+PrefManager.getLanguage()=="ar"){
        view.positiveBtn.text = "إذهب للرئيسية"
    }
    else{
        view.positiveBtn.text = getString(R.string.go_to_home)
    }

    view.positiveBtn.setOnClickListener{positive(dialog)}
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    //   view.negativeButton.setOnClickListener{negative(dialog)}
    dialog.show()
}



fun Context.showPaymentFailDialog(response : String, positive:(dialog: AlertDialog?) -> Unit){
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_cart_fails, null)
    val alertDialog = AlertDialog.Builder(this).setView(view)
        .setCancelable(false)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(false)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    Util.changeViewTypeFace(this,
        Constants.FONT_REGULAR,
        view.total_info,
        view.positiveBtn)
    //  view.dialogIcon.setImageResource(drawableRes)
    // view.dialogText.text = resID
    view.total_info.text = response
    if (""+PrefManager.getLanguage()=="ar"){
        view.positiveBtn.text = "إذهب للرئيسية"
    }
    else{
        view.positiveBtn.text = getString(R.string.go_to_home)
    }

    view.positiveBtn.setOnClickListener{positive(dialog)}
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    //   view.negativeButton.setOnClickListener{negative(dialog)}
    dialog.show()
}



fun Context.showEnterPrintNameDialog( positive:(dialog: AlertDialog?,name : String) -> Unit){
    val view = LayoutInflater.from(this).inflate(R.layout.dialog_enter_name, null)
    val alertDialog = AlertDialog.Builder(this).setView(view)
        .setCancelable(true)
    val dialog = alertDialog.create()
    dialog.setCanceledOnTouchOutside(true)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    Util.changeViewTypeFace(this,
        Constants.FONT_REGULAR,
        view.positiveBtn)
    //  view.dialogIcon.setImageResource(drawableRes)
    // view.dialogText.text = resID
   // view.total_price.text = total
    view.positiveBtn.setOnClickListener{positive(dialog,view.enter_et.text.toString())}
    //if (drawableRes == R.drawable.dialog_check) view.negativeButton.visibility =View.GONE
    //   view.negativeButton.setOnClickListener{negative(dialog)}
    dialog.show()
}






fun ImageView.loadImage(url: String) {
     doOnPreDraw {
    Glide.with(this).load(url).into(this)
    }
}

fun ImageView.loadImage(url: String, width : Int, height : Int) {
    //doOnPreDraw {
        Glide.with(this).load(url).centerCrop().override(width,height).into(this)
   // }
}
fun ImageView.loadImageFit(url: String, width : Int, height : Int) {
  //  doOnPreDraw {
        Glide.with(this).load(url).fitCenter()
            .override(width,height).into(this)
  //  }
}


fun ImageView.loadImage(@DrawableRes drawableRes: Int) {
    Glide.with(this).load(drawableRes).into(this)
}

fun ImageView.loadCircleImage(url: String?) {
  //   doOnPreDraw {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .apply(RequestOptions().error(R.drawable.ic_profile).placeholder(R.drawable.ic_profile))
        .into(this)
   //  }
}

fun ImageView.loadCircleImage(bitmap: Bitmap) {
    //  doOnPreDraw {
    Glide.with(this)
        .asBitmap().load(bitmap)
        .circleCrop()
        .apply(RequestOptions().error(R.drawable.ic_profile_gray).placeholder(R.drawable.ic_profile_gray))
        .into(this)
  //   }
}

fun ImageView.loadCircleImageGrayPlaceholder(url: String?) {
   // doOnPreDraw {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .apply(RequestOptions().error(R.drawable.ic_profile_gray).placeholder(R.drawable.ic_profile_gray))
            .into(this)
  //  }
}

fun Bitmap.toStringBase(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun Context.isPortrait() =
    resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

fun Context.showLoadingDialog() :Dialog{
    val view = LayoutInflater.from(this).inflate(R.layout.custom_progress_dialog_layout, null)
    val alertDialog = AlertDialog.Builder(this).setView(view)
    val dialog = alertDialog.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
  // view.loading.loadImage(R.drawable.loading)
   // dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    dialog.show()

    return dialog
}



class Util{
    companion object {

        fun manipulateToolbar(mContext: AppCompatActivity,
                              toolbar: Toolbar,
                              navigationIcon: Int,
                              onClickNavigationAction: () -> Unit,
                              isCustoomTitle: Boolean) {
            mContext.setSupportActionBar(toolbar)

            if (navigationIcon != 0) {
                toolbar.setNavigationIcon(navigationIcon)
                toolbar.setNavigationOnClickListener{
                    onClickNavigationAction()
                }
            }

            if (isCustoomTitle) {
                /*
            * hide title
            * */
                if (mContext.supportActionBar != null)
                    mContext.supportActionBar!!.setDisplayShowTitleEnabled(false)
                //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
                toolbar.setTitle("")
                toolbar.setSubtitle("")
            }
        }

        /**
         * @param mContext current context
         * @param fontPath path to font.ttf ex. "fonts/normal.ttf" if there fonts directory under assets
         * @param views    that views you want to change it type face should extend text view
         */
        fun changeViewTypeFace(mContext: Context?, fontPath: String, vararg views: View) {
            val font = Typeface.createFromAsset(mContext?.getAssets(), fontPath)
            for (v in views) {
                (v as? TextView)?.typeface = font
                if(v is Button)
                    v.typeface = font
            }

        }



        fun changeFontOfNavigation(mContext: Context, navigationView: NavigationView) {
            val m = navigationView.getMenu()
            for (i in 0 until m.size()) {
                val mi = m.getItem(i)

                //for aapplying a font to subMenu ...
                val subMenu = mi.getSubMenu()
                if (subMenu != null && subMenu!!.size() > 0) {
                    for (j in 0 until subMenu!!.size()) {
                        val subMenuItem = subMenu!!.getItem(j)
                        applyFontToMenuItem(mContext, subMenuItem)
                    }
                }

                //the method we have create in activity
                applyFontToMenuItem(mContext, mi)
            }
        }

        fun applyFontToMenuItem(mContext: Context, mi: MenuItem) {
            val font = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT_REGULAR)
            val mNewTitle = SpannableString(mi.getTitle())
            mNewTitle.setSpan(CustomTypefaceSpan("", font), 0, mNewTitle.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            mi.setTitle(mNewTitle)
        }


         fun changeTabsFont(mContext: Context,tabLayout : TabLayout ) {

             val font = Typeface.createFromAsset(mContext.getAssets(), Constants.FONT_REGULAR)

            val vg = tabLayout.getChildAt(0) as ViewGroup
            val tabsCount = vg.childCount
            for (j in 0 until tabsCount) {
                val vgTab = vg.getChildAt(j) as ViewGroup
                val tabChildsCount = vgTab.childCount
                for (i in 0 until tabChildsCount) {
                    val tabViewChild = vgTab.getChildAt(i)
                    if (tabViewChild is TextView) {
                        tabViewChild.typeface = font
                    }
                }
            }
        }




    }



}
private class CustomTypefaceSpan(family: String, private val newType: Typeface) : TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
        val oldStyle: Int
        val old = paint.typeface
        if (old == null) {
            oldStyle = 0
        } else {
            oldStyle = old.style
        }

        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}
