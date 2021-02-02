//package com.dev.mahmoud_ashraf.al_ahlysportsclub.Homesnap
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.engine.DiskCacheStrategy
//import com.bumptech.glide.load.resource.bitmap.CenterCrop
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.Base.Constants
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.Base.Util
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.R
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mHome
//
//class HorizontalAdapter(
//    private val items: List<*> , private val count : Int , private val flag : Int
//) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
//
//    val item_news = 0
//    val item_albums = 1
//    val item_videos = 2
//    val item_products = 3
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val title: TextView = itemView.findViewById(R.id.glorious_number) as TextView
//        val image : ImageView = itemView.findViewById(R.id.categoryIv) as ImageView
//        init {
//            changeViewsFonts()
//        }
//
//        private fun changeViewsFonts() {
//            Util.changeViewTypeFace(itemView.context,
//                Constants.FONT_REGULAR,
//                title)
//        }
//    }
//
//    override fun onCreateViewHolder(
//        viewGroup: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//        val inflater = LayoutInflater.from(viewGroup.context)
//        val view = inflater.inflate(R.layout.item_horizontal, viewGroup, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (holder.itemViewType== item_news){
//            val news = items[position] as  mHome.New
//            holder.title.text = news.name
//
//            Glide.with( holder.image).
//                load(Constants.baseUrl + news.image)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .transforms( CenterCrop(), RoundedCorners(6))
//                .thumbnail(0.1f)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                //.crossFade()
//
//                //.override(300,157)
//                //.centerCrop()
//                .into( holder.image)
//
//
//
//        }
//       else if (holder.itemViewType == item_albums){
//            val news = items[position] as  mHome.Album
//            holder.title.text = news.name
//
//            Glide.with( holder.image).
//                load(Constants.baseUrl + news.image)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .transforms( CenterCrop(), RoundedCorners(6))
//                .thumbnail(0.1f)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                //.crossFade()
//
//                //.override(300,157)
//                //.centerCrop()
//                .into( holder.image)
//
//        }
//       else if (holder.itemViewType == item_videos){
//            val news = items[position] as  mHome.Video
//            holder.title.text = news.name
//
//            Glide.with( holder.image).
//                load(Constants.baseUrl + news.image)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .transforms( CenterCrop(), RoundedCorners(6))
//                .thumbnail(0.1f)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                //.crossFade()
//
//                //.override(300,157)
//                //.centerCrop()
//                .into( holder.image)
//
//        }
//       else if (holder.itemViewType == item_products){
//            val news = items[position] as  mHome.Product
//            holder.title.text = news.name
//
//            Glide.with( holder.image).
//                load(Constants.baseUrl + news.image)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .transforms( CenterCrop(), RoundedCorners(6))
//                .thumbnail(0.1f)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                //.crossFade()
//
//                //.override(300,157)
//                //.centerCrop()
//                .into( holder.image)
//
//        }
//
//
//       // val title = titles[position]
//      //  holder.title.text = title
//    }
//
//    override fun getItemCount(): Int {
//        return count
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if (flag == 0)item_news
//        else if (flag == 1) item_albums
//        else if (flag == 2) item_videos
//        else if (flag ==3) item_products
//        else return -1
//    }
//
//
//}