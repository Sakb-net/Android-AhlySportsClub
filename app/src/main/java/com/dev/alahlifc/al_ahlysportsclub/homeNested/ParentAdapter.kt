package com.dev.alahlifc.al_ahlysportsclub.homeNested

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.loadImageFit
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import kotlinx.android.synthetic.main.item_horizontal_ads_row.view.*
import kotlinx.android.synthetic.main.item_horizontal_ads_row.view.team1_iv
import kotlinx.android.synthetic.main.item_horizontal_ads_row.view.team1_tv
import kotlinx.android.synthetic.main.item_horizontal_ads_row.view.team2_iv
import kotlinx.android.synthetic.main.item_horizontal_ads_row.view.team2_tv
import kotlinx.android.synthetic.main.item_vertical.view.*
import kotlinx.android.synthetic.main.item_vertical.view.title_tv


class ParentAdapter(private val parents : List<Pair<String, mHome.Data?>?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

 //   private val viewPool = RecyclerView.RecycledViewPool()
    var onItemClick: ((positon:Int) -> Unit)? = null

    val item_ads = 0
    val item_normal = 1


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val adapt = ChildAdapter(null)

        init {
            // add listener
            /**itemView.buy_.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }*/

            itemView.more.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }



            itemView.horizontal_recyclerview.apply {
                layoutManager = LinearLayoutManager(itemView.horizontal_recyclerview.context,
                    LinearLayout.HORIZONTAL, false)
//                setItemAnimator(DefaultItemAnimator())
//                setHasFixedSize(true)
//                setItemViewCacheSize(20)
//                setDrawingCacheEnabled(true)
//                setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
//                getRecycledViewPool().setMaxRecycledViews(1, 0);
                 adapter = adapt

            }





//            GravitySnapHelper(Gravity.START)
//                .attachToRecyclerView(itemView.horizontal_recyclerview)
//            with(itemView.horizontal_recyclerview) {
//                adapter = horizontalAdapter
//                requestDisallowInterceptTouchEvent(true)
//            }
        }



    }

    inner class AdsHolder(itemView : View) : RecyclerView.ViewHolder(itemView){


        init {
            // add listener
           itemView.buy_.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }

       /*     itemView.more.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
*/




//            GravitySnapHelper(Gravity.START)
//                .attachToRecyclerView(itemView.horizontal_recyclerview)
//            with(itemView.horizontal_recyclerview) {
//                adapter = horizontalAdapter
//                requestDisallowInterceptTouchEvent(true)
//            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            item_ads ->{

                val v = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_ads,parent,false)
                v.run {
                    Util.changeViewTypeFace(parent.context, Constants.FONT_REGULAR,
                         buy_ ,team1_tv, team2_tv, title_tv_, date_tv )
                }

                AdsHolder(v)}
            item_normal -> {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical,parent,false)
                v.run {

                    Util.changeViewTypeFace(parent.context, Constants.FONT_REGULAR, more)

                    Util.changeViewTypeFace(parent.context, Constants.FONT_BOLD,
                        title_tv )
                }
               val vh = ViewHolder(v)
        ///    vh.itemView.horizontal_recyclerview.setRecycledViewPool ( viewPool)
                vh
            }
            else ->  {
                val v = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical,parent,false)
                v.run {

                    Util.changeViewTypeFace(parent.context, Constants.FONT_REGULAR, more)

                    Util.changeViewTypeFace(parent.context, Constants.FONT_BOLD, title_tv )
                }

                val vh = ViewHolder(v)
               // vh.itemView.horizontal_recyclerview.setRecycledViewPool ( viewPool)
                vh
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = parents[position]
       // holder.textView.text = parent.title


        when(holder.itemViewType){
            item_ads -> {
                val viewHolder0 = holder as AdsHolder

                viewHolder0.itemView.apply{
                    val ads = item!!.second!!
                    ads?.matchNext
                    date_tv.text = ads?.matchNext?.time.toString() + ", " + ads?.matchNext?.date.toString()
                    team1_tv.text = ads?.matchNext?.firstTeam.toString()
                    team2_tv.text = ads?.matchNext?.secondTeam.toString()
                    team1_iv.loadImageFit(Constants.baseUrl + ads?.matchNext?.firstImage.toString(), 40, 40)
                    team2_iv.loadImageFit(Constants.baseUrl + ads?.matchNext?.secondImage.toString(), 40, 40)
                }

            }



            item_normal ->{
                val viewHolder1 = holder as ViewHolder



                viewHolder1.itemView.apply {


                        when (item!!.first) {



                            "news" -> {
                                //   constraint_container.visibility = View.VISIBLE
                                //     constraint_ads.visibility = View.GONE
                                title_tv.text = context.getString(R.string.latest_news)
                                val news = item.second?.news
                                //horizontalAdapter.submitList(news?.toMutableList() as List<Any>?)
                                holder.adapt.updateData( news?.toMutableList() as List<Any>?)

                            }

                            "albumm" -> {

                                //   constraint_container.visibility = View.VISIBLE
                                // constraint_ads.visibility = View.GONE

                                title_tv.text = context.getString(R.string.latest_albums)
                                val albumm = item.second?.albums
                                //  horizontalAdapter.submitList(albumm?.toMutableList() as List<Any>?)
                                holder.adapt.updateData(albumm?.toMutableList() as List<Any>?)
                            }

                            "videos" -> {
                                //constraint_container.visibility = View.VISIBLE
                                //  constraint_ads.visibility = View.GONE
                                title_tv.text = context.getString(R.string.latest_videos)
                                val videos = item.second?.videos
                                // horizontalAdapter.submitList(videos?.toMutableList() as List<Any>?)
                                holder.adapt.updateData(videos?.toMutableList() as List<Any>?)
                            }
                            "products" -> {
                                //constraint_container.visibility = View.VISIBLE
                                //  constraint_ads.visibility = View.GONE
                                title_tv.text = context.getString(R.string.latest_products)
                                val products = item.second?.products
                                //horizontalAdapter.submitList(products?.toMutableList() as List<Any>?)
                                holder.adapt.updateData(products?.toMutableList() as List<Any>?)
                            }
                        }
                    }

                }





            }

        }/**
//        if (item != null)
//            holder.itemView.apply {
//
//                    when (item.first) {
////                        "ads" -> {
////
////                          //  constraint_container.visibility = View.GONE
////                         //   constraint_ads.visibility = View.VISIBLE
////                            val ads = item.second
////                            ads?.matchNext
////
////                            date_tv.text = ads?.matchNext?.time.toString() + ", " +
////                                    ads?.matchNext?.date.toString()
////
////                            team1_tv.text = ads?.matchNext?.firstTeam.toString()
////                            team2_tv.text = ads?.matchNext?.secondTeam.toString()
////
////                            team1_iv.loadImageFit(Constants.baseUrl + ads?.matchNext?.firstImage.toString(), 40, 40)
////                            team2_iv.loadImageFit(Constants.baseUrl + ads?.matchNext?.secondImage.toString(), 40, 40)
////                        }
//
//
//                        "news" -> {
//                         //   constraint_container.visibility = View.VISIBLE
//                       //     constraint_ads.visibility = View.GONE
//                            title_tv.text = context.getString(R.string.latest_news)
//                            val news = item.second?.news
//                            //horizontalAdapter.submitList(news?.toMutableList() as List<Any>?)
//                            holder.itemView.horizontal_recyclerview.adapter = ChildAdapter(news?.toMutableList() as List<Any>?)
//
//                        }
//
//                        "albumm" -> {
//
//                         //   constraint_container.visibility = View.VISIBLE
//                           // constraint_ads.visibility = View.GONE
//
//                            title_tv.text = context.getString(R.string.latest_albums)
//                            val albumm = item.second?.albums
//                            //  horizontalAdapter.submitList(albumm?.toMutableList() as List<Any>?)
//                            holder.itemView.horizontal_recyclerview.adapter = ChildAdapter(albumm?.toMutableList() as List<Any>?)
//                        }
//
//                        "videos" -> {
//                            //constraint_container.visibility = View.VISIBLE
//                          //  constraint_ads.visibility = View.GONE
//                            title_tv.text = context.getString(R.string.latest_videos)
//                            val videos = item.second?.videos
//                            // horizontalAdapter.submitList(videos?.toMutableList() as List<Any>?)
//                            holder.itemView.horizontal_recyclerview.adapter = ChildAdapter(videos?.toMutableList() as List<Any>?)
//                        }
//                        "products" -> {
//                            //constraint_container.visibility = View.VISIBLE
//                          //  constraint_ads.visibility = View.GONE
//                            title_tv.text = context.getString(R.string.latest_products)
//                            val products = item.second?.products
//                            //horizontalAdapter.submitList(products?.toMutableList() as List<Any>?)
//                            holder.itemView.horizontal_recyclerview.adapter = ChildAdapter(products?.toMutableList() as List<Any>?)
//                        }
//                    }
//                }*/



    override fun getItemCount(): Int = 5

    override fun getItemViewType(position: Int): Int {
        return if (position==0)item_ads else item_normal
    }



}