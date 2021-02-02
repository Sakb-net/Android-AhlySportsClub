package com.dev.alahlifc.al_ahlysportsclub.ProductDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemProductDetailsSliderBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mProductSingle

class ProductDetailsSliderAdapter(private val mutableList: MutableList<mProductSingle.Data.AnotherImage>) :
    RecyclerView.Adapter<ProductDetailsSliderAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mutableList[position].name)
    }

    class ViewHolder(val binding: ItemProductDetailsSliderBinding) : RecyclerView.ViewHolder(binding.root) {

         fun bind(url : String) {
             Glide.with(binding.productImg.context).
                 load(Constants.baseUrl +url)
                 .transition(DrawableTransitionOptions.withCrossFade())
                 .thumbnail(0.1f)
                 .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                 .centerCrop()
                 .into( binding.productImg)
         }
     }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemProductDetailsSliderBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_product_details_slider, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = mutableList.size

}