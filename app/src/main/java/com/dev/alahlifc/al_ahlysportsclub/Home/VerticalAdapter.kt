package com.dev.alahlifc.al_ahlysportsclub.Home
import android.view.Gravity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.loadImageFit
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import com.dev.alahlifc.al_ahlysportsclub.utils.SparseIntArrayParcelable
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.item_horizontal_ads_row.view.*
import kotlinx.android.synthetic.main.item_vertical.view.*
import kotlinx.android.synthetic.main.item_vertical.view.title_tv

class VerticalAdapter(val horizontalScrollPositions: SparseIntArrayParcelable)
// extend
    : ListAdapter<Pair<String,mHome.Data?>?, VerticalAdapter.VerticalViewHolder>(VerticalDiffCallback) {

    private val horizontalViewPool = RecyclerView.RecycledViewPool()

    var onItemClick: ((positon:Int) -> Unit)? = null

    companion object {
       // private const val PORTRAIT_PREFETCH = 10
       // private const val LANDSCAPE_PREFETCH = 14
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vertical, parent, false)
            .run { VerticalViewHolder(this) }
            .apply {
             //  val isPortrait = parent.context.isPortrait()
              //  getHorizontalLayoutManager().initialPrefetchItemCount = if (isPortrait) {
                //    PORTRAIT_PREFETCH
             //   } else {
             //       LANDSCAPE_PREFETCH
             //   }
                getHorizontalRecyclerView().setRecycledViewPool(horizontalViewPool)
            }

    override fun onBindViewHolder(item: VerticalViewHolder, position: Int) {
        val scrollPosition = horizontalScrollPositions.get(position, 0)

        if (getItem(position)!=null)
            item.bindViewHolder(getItem(position), scrollPosition)
    }

    override fun onViewDetachedFromWindow(holder: VerticalViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val itemPosition = holder.adapterPosition
        val scrollPosition = holder.findFirstVisibleItemPosition()
        horizontalScrollPositions.put(itemPosition, scrollPosition)
    }

    inner class VerticalViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val horizontalAdapter = HorizontalAdapter()
        ///  private val snapHelper = StartSnapHelper()

        init {
            // add listener
            itemView.buy_.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }

            itemView.more.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }


            GravitySnapHelper(Gravity.START)
                .attachToRecyclerView(itemView.horizontal_recyclerview)
            with(itemView.horizontal_recyclerview) {
                adapter = horizontalAdapter
                requestDisallowInterceptTouchEvent(true)
            }
        }

        fun bindViewHolder(item:Pair<String,mHome.Data?>?, scrollPosition: Int) {

            if (item != null)
            {
                when(item.first){
                    "ads" -> {
                     //   itemView.constraint_container.visibility  = View.GONE
                       // itemView.constraint_ads.visibility  = View.VISIBLE
                        val ads = item.second
                        ads?.matchNext

                        itemView.date_tv.text =  ads?.matchNext?.time.toString() + ", "+
                                ads?.matchNext?.date.toString()

                        itemView.team1_tv.text =   ads?.matchNext?.firstTeam.toString()
                        itemView.team2_tv.text =   ads?.matchNext?.secondTeam.toString()

                        itemView.team1_iv.loadImageFit(Constants.baseUrl+ ads?.matchNext?.firstImage.toString(),40,40)
                        itemView.team2_iv.loadImageFit(Constants.baseUrl+ ads?.matchNext?.secondImage.toString(),40,40)
                    }


                    "news" -> {
                       // itemView.constraint_container.visibility  = View.VISIBLE
                     //   itemView.constraint_ads.visibility  = View.GONE
                        itemView.title_tv.text = itemView.context.getString(R.string.latest_news)
                        val news = item.second?.news
                        horizontalAdapter.submitList(news?.toMutableList() as List<Any>?)

                    }

                    "albumm" -> {

                      //  itemView.constraint_container.visibility  = View.VISIBLE
                        //itemView.constraint_ads.visibility  = View.GONE

                        itemView.title_tv.text = itemView.context.getString(R.string.latest_albums)
                        val albumm = item.second?.albums
                        horizontalAdapter.submitList(albumm?.toMutableList() as List<Any>?)
                    }

                    "videos" ->{
                     //   itemView.constraint_container.visibility  = View.VISIBLE
                      //  itemView.constraint_ads.visibility  = View.GONE
                        itemView.title_tv.text = itemView.context.getString(R.string.latest_videos)
                        val videos = item.second?.videos
                        horizontalAdapter.submitList(videos?.toMutableList() as List<Any>?)
                    }
                    "products" ->{
                        //itemView.constraint_container.visibility  = View.VISIBLE
                      //  itemView.constraint_ads.visibility  = View.GONE
                        itemView.title_tv.text = itemView.context.getString(R.string.latest_products)
                        val products = item.second?.products
                        horizontalAdapter.submitList(products?.toMutableList() as List<Any>?)
                    }
                }
            }


            getHorizontalLayoutManager().scrollToPositionWithOffset(scrollPosition, 0)
        }

        fun findFirstVisibleItemPosition() =
            getHorizontalLayoutManager().findFirstVisibleItemPosition()

        fun getHorizontalLayoutManager() =
            getHorizontalRecyclerView().layoutManager as LinearLayoutManager

        fun getHorizontalRecyclerView(): RecyclerView =
            itemView.horizontal_recyclerview
    }
}