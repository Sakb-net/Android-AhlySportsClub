package com.dev.alahlifc.al_ahlysportsclub.AboutClub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemAboutclubSliderBinding

class AboutClubSliderAdapter(private val mutableList: MutableList<Pair<String,String>>) :
    RecyclerView.Adapter<AboutClubSliderAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mutableList[position].first,mutableList[position].second)
    }




     class ViewHolder(val binding: ItemAboutclubSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title : String, content :String) {

            binding.titleTv.text = title
            binding.aboutclubTv.text = content
          /*  with(itemView) {
                title_tv.text = title
                aboutclub_tv.text = content
            }*/
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
       // val v =  layoutInflater.inflate(R.layout.item_aboutclub_slider, parent, false)
        val binding: ItemAboutclubSliderBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_aboutclub_slider, parent, false)

        Util.changeViewTypeFace(parent.context, Constants.FONT_REGULAR,
            binding.titleTv,binding.aboutclubTv)
      return ViewHolder(
          binding
        )
    }

    override fun getItemCount(): Int = mutableList.size

}