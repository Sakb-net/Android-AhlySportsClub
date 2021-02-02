package com.dev.alahlifc.al_ahlysportsclub.Base

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.dev.alahlifc.al_ahlysportsclub.models.mProductSingle
import timber.log.Timber

@BindingAdapter("srcNormal")
    fun setImageUrl(view: ImageView, url: String) {
        Glide.with(view.context).load(url).into(view)
    }

@BindingAdapter("arr")
fun BindArr(view: TextView, data : List<mProductSingle.Data.Weight>?) {
    if (!data.isNullOrEmpty())
    for (i in data.indices) {
        if (i!=0)
        view.text= view.text.toString() + " - " + data[i].name.toString()
        else
            view.text=  data[i].name.toString()
    }
}
@BindingAdapter("arr")
fun BindArrColor(view: TextView, data : List<mProductSingle.Data.Color>?) {

    if (!data.isNullOrEmpty())
    for (i in data.indices) {
        if (i!=0)
        view.text= view.text.toString() + " - " + data[i].name.toString()
        else
        view.text=  data[i].name.toString()
    }
}



    @BindingAdapter("srcNormal")
    fun setImageUrl(view: ImageView, IdRes : Int ) {
        Glide.with(view.context).load(IdRes).into(view)
    }  @BindingAdapter("srcNormalproduct")
    fun setProductImg(view: ImageView, url: String="" ) {
       // Glide.with(view.context).load(IdRes).into(view)
    Glide.with(view).
        load(Constants.baseUrl +url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .transforms( CenterCrop(), RoundedCorners(6))
        .thumbnail(0.1f)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        //.crossFade()

        //.override(300,157)
        //.centerCrop()
        .into( view)
    }

@BindingAdapter("srcVideoImage")
fun setImageVideoUrl(view: ImageView,  url: String ) {
    Glide.with(view.context).
        load(Constants.baseUrl + url)
        .transition(withCrossFade())
        .transforms( CenterCrop(),RoundedCorners(6))
        .thumbnail(0.1f)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        //.crossFade()

        //.override(300,157)
        //.centerCrop()
        .into(view)


}

@BindingAdapter("srcVideoImageYtube")
fun setImageVideoUrlYoutube(view: ImageView,  key: String ) {
    Glide.with(view.context).
      //  load(Constants.baseUrl + url)
    load("http://img.youtube.com/vi/"+key+"/default.jpg")
        .transition(withCrossFade())
        .transforms( CenterCrop(),RoundedCorners(6))
        .thumbnail(0.1f)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        //.crossFade()

        //.override(300,157)
        //.centerCrop()
        .into(view)


}

@BindingAdapter("srcCustomImage")
fun setImageCategoryUrl(view: ImageView,  url: String ) {
   // Timber.e("from biniding-----------"+view.height)
    Glide.with(view)
        .load(Constants.baseUrl + url)
        .transition(withCrossFade())
        .override(200,200)
        .transforms(RoundedCorners(6))
        .thumbnail(0.1f)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .fitCenter()
        .into(view)
}

@BindingAdapter("srcCustomImageChamp")
fun srcCustomImageChamp(view: ImageView,  url: String ) {
    // Timber.e("from biniding-----------"+view.height)
    Glide.with(view.context)
        .load(Constants.baseUrl + url)
       // .transition(withCrossFade())
        //.override(200,200)
       // .transforms(RoundedCorners(6))
        .thumbnail(0.1f)
       // .diskCacheStrategy(DiskCacheStrategy.NONE)
        //.fitCenter()
        .into(view)
}

@BindingAdapter("srcCartImage")
fun setImageCartUrl(view: ImageView,  url: String ) {
    Timber.e("from biniding-----------"+view.height)
    Glide.with(view.context).load(Constants.baseUrl + url).override(view.width,view.height).thumbnail(.8f)

        // .placeholder(R.drawable.ic_iconfinder_no_image_png_)
        //  .error(R.drawable.ic_iconfinder_no_image_png_)

        //.fitCenter()
        .into(view)
}


@BindingAdapter("srcTeamImage")
fun setTeamImageUrl(view: ImageView,  url: String?) {
    Timber.e("from biniding-----------"+view.height)
    Glide.with(view.context).load(Constants.baseUrl + url).override(view.width,view.height).thumbnail(.8f)

        // .placeholder(R.drawable.ic_iconfinder_no_image_png_)
        //  .error(R.drawable.ic_iconfinder_no_image_png_)

        .fitCenter()
        .into(view)
}

    @BindingAdapter("srcCircle")
    fun setCircleImageUrl(view: ImageView, url: String? ) {
        view.loadCircleImage(Constants.baseUrl + url)
    }

@BindingAdapter("srcCircleGrayPlaceholder")
fun setCircleImageUrlGrayPlaceholder(view: ImageView, url: String? ) {
    view.loadCircleImageGrayPlaceholder(url)
}





