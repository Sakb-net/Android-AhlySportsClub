package com.dev.alahlifc.al_ahlysportsclub.Homesnap

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.loadImage
import com.dev.alahlifc.al_ahlysportsclub.BookTicket.BookTicketActivity
import com.dev.alahlifc.al_ahlysportsclub.Categories.CategoriesActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import kotlinx.android.synthetic.main.adapter_splash.view.*
import kotlinx.android.synthetic.main.item_horizontal_ads_row.view.*

class SplashAdapter(private val context: Context,private val ads : mHome.Data?) : PagerAdapter() {

    private var ui : View?= null
    var onItemClick: ((position : Int) -> Unit)? = null

    private fun getOnBoardingImage(): TypedArray = context.resources.obtainTypedArray(R.array.on_boarding_image)

    override fun getCount(): Int = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) { container.removeView(`object` as ConstraintLayout) }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        ui = LayoutInflater.from(context).inflate(R.layout.adapter_splash, null)
        container.addView(ui)



        if(position==0) {
            ui!!.buy_.visibility = View.VISIBLE
            ui!!.title_tv_.visibility = View.VISIBLE
            ui!!.textView7.visibility = View.VISIBLE
            ui!!.date_tv.text = ads?.matchNext?.time.toString() + ", " + ads?.matchNext?.date.toString()
            ui!!.team1_tv.text = ads?.matchNext?.firstTeam.toString()
            ui!!.team2_tv.text = ads?.matchNext?.secondTeam.toString()
            ui!!.team1_iv.loadImage(Constants.baseUrl + ads?.matchNext?.firstImage.toString())
            ui!!.team2_iv.loadImage(Constants.baseUrl + ads?.matchNext?.secondImage.toString())

            ui!!.buy_.setOnClickListener {
                //  Timber.e("dddd"+data.data.matchNext.link_ticket)
                context?.startActivity(Intent(context, BookTicketActivity::class.java)
                    .putExtra("link", ads?.matchNext?.link_ticket))
            }
        }
        else{
            ui!!.buy_.visibility = View.VISIBLE
            ui!!.buy_.text = context.resources.getString(R.string.buy_now)
            ui!!.title_prod.visibility = View.VISIBLE

            ui!!.buy_.setOnClickListener {
                //  Timber.e("dddd"+data.data.matchNext.link_ticket)
               onItemClick?.invoke(1)
            }




        }


        Glide.with(ui!!.context).load(getOnBoardingImage().getResourceId(position, 0))
           // .transition(DrawableTransitionOptions.withCrossFade())
            //.thumbnail(0.1f)
            .centerCrop()
            //.skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(ui!!.backgroudIv)
      ///  ui.tv_on_boarding_title.text = getOnBoardingTitle()[position]
        ///ui.tv_on_boarding_subtitle.text = getOnBoardingSubtitle()[position]
        getOnBoardingImage().recycle()



       /// ui.tv_on_boarding_title.alpha = 0f
       /// ui.tv_on_boarding_title.translationY = 500f

        ///ui.tv_on_boarding_subtitle.alpha = 0f
        ///ui.tv_on_boarding_subtitle.translationY = 500f

      //  ui.iv_on_boarding_image.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bounce))
       /// ui.tv_on_boarding_title.animate().alpha(1f).translationY(0f).setDuration(800).setStartDelay(300).start()
        ///ui.tv_on_boarding_subtitle.animate().alpha(1f).translationY(0f).setDuration(800).setStartDelay(600).start()

        ///ui.next_btn.setOnClickListener {
         //   context.startActivity(Intent(context, HomeActivity::class.java))
        ///}
        return ui!!
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object` as ConstraintLayout


}