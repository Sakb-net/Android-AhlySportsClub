//package com.dev.mahmoud_ashraf.al_ahlysportsclub.Home
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.Base.Constants
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.Base.loadImage
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.R
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.databinding.ItemHorizontalBinding
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.databinding.ItemNewsBinding
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mHome
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mNews
//
//
//class NewsSectionAdapter(private val mutableList:List< mHome.New?>?) :
//    RecyclerView.Adapter<NewsSectionAdapter.ViewHolder>() {
//
//
//    class ViewHolder(val binding: ItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
//
//
//        fun bind(data: Any) {
//            val item = data as mHome.New
//
//            binding.gloriousNumber.text = item.name.toString()
//            binding.categoryIv.loadImage(Constants.baseUrl+item.image.toString(),230,136)
//
//            //binding.newsData = data as mNews.Data
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        val layoutInflater = LayoutInflater.from(parent.context)
//
//        val binding: ItemHorizontalBinding =
//            DataBindingUtil.inflate(layoutInflater, R.layout.item_horizontal, parent, false)
//
//    return  ViewHolder(binding)
//    }
//
//
//
//
//
//
//    override fun getItemCount(): Int = mutableList!!.size
//
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
//            holder.bind(mutableList!![position]!!)
//    }
//
//
//
//
//
//}