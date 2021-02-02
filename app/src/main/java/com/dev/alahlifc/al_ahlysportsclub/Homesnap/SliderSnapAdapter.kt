package com.dev.alahlifc.al_ahlysportsclub.Homesnap

import android.content.Intent
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.loadImage
import com.dev.alahlifc.al_ahlysportsclub.BookTicket.BookTicketActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import kotlinx.android.synthetic.main.item_horizontal_slider.view.*
import timber.log.Timber

class SliderSnapAdapter(private val items: mHome.Data) : RecyclerView.Adapter<SliderSnapAdapter.ViewHolder>() {

    var onItemClick: ((position : Int) -> Unit)? = null
    var onPageChanged : ((position : Int) -> Unit)? = null



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.item_horizontal_slider, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

         val getOnBoardingImage: TypedArray = holder.itemView.context.resources.obtainTypedArray(R.array.on_boarding_image)

      //  Timber.e("bind+        "+position)
      //  onPageChanged?.invoke(position)
        val ads = items

        if(position==0) {


            if (ads?.matchNext!=null)
            ads?.matchNext?.let {
                holder.itemView.buy_.visibility = View.VISIBLE
                holder.itemView.title_tv_.visibility = View.VISIBLE
                holder.itemView.textView7.visibility = View.VISIBLE
                holder.itemView.date_tv.text = it?.time?.toString() + ", " + ads?.matchNext?.date?.toString()
                holder.itemView.team1_tv.text = it?.firstTeam.toString()
                holder.itemView.team2_tv.text = it?.secondTeam.toString()
                holder.itemView.team1_iv.loadImage(Constants.baseUrl + it?.firstImage.toString())
                holder.itemView.team2_iv.loadImage(Constants.baseUrl +it?.secondImage.toString())

                holder.itemView.buy_.setOnClickListener {_->
                    //  Timber.e("dddd"+data.data.matchNext.link_ticket)
                    holder.itemView. context?.startActivity(
                        Intent(holder.itemView.context, BookTicketActivity::class.java)
                            .putExtra("link", it?.link_ticket))
                }

            }
            else {
                holder.itemView.buy_.visibility = View.VISIBLE
                holder.itemView.buy_.text = holder.itemView.resources.getString(R.string.buy_now)
                holder.itemView.title_prod.visibility = View.VISIBLE

                holder.itemView.buy_.setOnClickListener {
                    onItemClick?.invoke(1)
                }

            }
        }
        else{
            holder.itemView.buy_.visibility = View.VISIBLE
            holder.itemView.buy_.text = holder.itemView.resources.getString(R.string.buy_now)
            holder.itemView.title_prod.visibility = View.VISIBLE

            holder.itemView.buy_.setOnClickListener {
                onItemClick?.invoke(1)
            }




        }




        Glide.with(holder.itemView.context).load(getOnBoardingImage.getResourceId(position, 0))
            .transition(DrawableTransitionOptions.withCrossFade())
            .thumbnail(0.1f)
            .centerCrop()
            //.skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.itemView.backgroudIv)


      ///  getOnBoardingImage.recycle()


    }

    override fun getItemCount(): Int {
        return 2
    }



}