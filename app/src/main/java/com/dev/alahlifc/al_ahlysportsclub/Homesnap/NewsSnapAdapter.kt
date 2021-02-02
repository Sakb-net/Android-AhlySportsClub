package com.dev.alahlifc.al_ahlysportsclub.Homesnap

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.NewsDetails.NewsDetailsActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import com.dev.alahlifc.al_ahlysportsclub.models.mNews

class NewsSnapAdapter(
    private val items: List<mHome.New> , private val count : Int , private val flag : Int
) : RecyclerView.Adapter<NewsSnapAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.glorious_number) as TextView
        val image : ImageView = itemView.findViewById(R.id.categoryIv) as ImageView
        init {
            changeViewsFonts()

            itemView.setOnClickListener {

                // mapping
                val obj = mNews.Data(items[adapterPosition].content,items[adapterPosition].createdAt,
                    items[adapterPosition].date,items[adapterPosition].description,items[adapterPosition].image
                ,items[adapterPosition].link,items[adapterPosition].name,items[adapterPosition].tags,
                    items[adapterPosition].userImage,items[adapterPosition].userName)


               itemView.context. startActivity(Intent( itemView.context, NewsDetailsActivity::class.java)
                   .apply {
                    putExtra("obj", obj)
                })
            }
        }

        private fun changeViewsFonts() {
            Util.changeViewTypeFace(itemView.context,
                Constants.FONT_REGULAR,
                title)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_horizontal, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            val news = items[position]
            holder.title.text = news.name

            Glide.with( holder.image).
                load(Constants.baseUrl + news.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transforms( CenterCrop(), RoundedCorners(6))
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                //.crossFade()

                //.override(300,157)
                //.centerCrop()
                .into( holder.image)


    }

    override fun getItemCount(): Int {
        return count
    }



}