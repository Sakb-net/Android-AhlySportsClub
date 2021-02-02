package com.dev.alahlifc.al_ahlysportsclub.Videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemLoadingBinding
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemVideosBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mVideos
import kotlinx.android.synthetic.main.item_loading.view.*
import timber.log.Timber


class VideosAdapter(private val mutableList: MutableList<mVideos.Data?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null

 inner class ViewHolder(val binding: ItemVideosBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Any) {
            if (data!=null){
            binding.videosData = data as mVideos.Data

            binding.root.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
                //binding.root.context.startActivity(Intent(binding.root.context, DetailsActivity::class.java))

            }
        }
        }

    }

    inner class LoadingViewHolder(val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Any) {

            if (data is Boolean)
                binding.root.progress_bar_.isIndeterminate = true

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        if (viewType== VIEW_TYPE_LOADING){
            val binding: ItemLoadingBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_loading, parent, false)
            return LoadingViewHolder(binding)
        }
        else {
            val binding: ItemVideosBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_videos, parent, false)

            return ViewHolder(binding)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (mutableList?.get(position) != null)
            VIEW_TYPE_ITEM
        else
            VIEW_TYPE_LOADING
    }

   /* fun addNullData() {
        mutableList?.add(mutableList.size,null)
        notifyItemInserted(mutableList!!.size)
    }
    fun removeNull() {
        mutableList!!.removeAt(mutableList.size-1)
        notifyItemRemoved(mutableList.size-1)
    }*/

//    fun addMoreItems(data: MutableList<mVideos.Data?>) {
//        val cur = itemCount
//        mutableList!!.addAll(itemCount,data)
//        notifyItemInserted(cur)
//    }

    override fun getItemCount(): Int = mutableList!!.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)  {

        if (holder is ViewHolder)
            holder.bind(mutableList!![position]!!)
        else{
            val viewHolder = holder as LoadingViewHolder
            viewHolder.bind(true) }
    }

    fun showLoading() {

        if(mutableList!!.get(mutableList!!.size-1)!=null) {

            Timber.e("before show loading- " + mutableList!!.size)
            Timber.e("before show loading- " + mutableList!!.get(mutableList.size - 1))
            mutableList!!.add(null)
            Timber.e("show loading- " + mutableList.size)
            Timber.e("show loading- " + mutableList.get(mutableList.size - 1))
            notifyItemInserted(mutableList!!.size - 1)
        }

    }

    fun addItems( data : MutableList<mVideos.Data?>) {

        Timber.e("before add items- "+mutableList!!.size)
        Timber.e("before add items- "+mutableList.get(mutableList.size-1))
        // remove loading


      /** if(mutableList!!.get( mutableList!!.size-1)==null) {

           Timber.e("beforeremov- "+mutableList.get( mutableList!!.size-1))

           mutableList!!.removeAt( mutableList!!.size-1)

           Timber.e("beforeremov- "+mutableList.size)
           notifyItemRemoved( mutableList!!.size - 1)
           Timber.e("beforeremov- "+mutableList.size)
       }*/

        val toAdd = mutableList!!.size - 1
        mutableList!!.addAll(toAdd, data)
      //  Timber.e("data sz- "+data.size)
        notifyItemRangeChanged(toAdd, mutableList!!.size)
      ///  Timber.e("data sz- "+mutableList.size)
    }


}