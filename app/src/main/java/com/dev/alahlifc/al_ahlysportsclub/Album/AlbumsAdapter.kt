///*
//package com.dev.mahmoud_ashraf.al_ahlysportsclub.Album
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.R
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.databinding.ItemAlbumBinding
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.databinding.ItemVideosBinding
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mAlbum
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mVideos
//
//
//class AlbumsAdapter(private val mutableList: MutableList<mAlbum.Data?>?) :
//    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {
//
//    private  val VIEW_TYPE_ITEM = 1
//    private  val VIEW_TYPE_LOADING = 2
//
//    class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
//
//
//        fun bind(data: Any) {
//            binding.albumData = data as mAlbum.Data
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        val layoutInflater = LayoutInflater.from(parent.context)
//
//        val binding: ItemAlbumBinding =
//            DataBindingUtil.inflate(layoutInflater, R.layout.item_album, parent, false)
//
//    return  ViewHolder(binding)
//    }
//
//
//    override fun getItemViewType(position: Int): Int {
//        return if (mutableList?.get(position) != null)
//            VIEW_TYPE_ITEM
//        else
//            VIEW_TYPE_LOADING
//    }
//
//    fun addNullData() {
//        mutableList?.add(mutableList.size,null)
//        notifyItemInserted(mutableList!!.size)
//    }
//
//    fun removeNull() {
//        mutableList!!.removeAt(mutableList.size-1)
//        notifyItemRemoved(mutableList.size-1)
//    }
//
//    fun addMoreItems(data: MutableList<mAlbum.Data?>) {
//        mutableList!!.addAll(mutableList!!.size,data)
//        notifyItemInserted(mutableList.size)
//    }
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
//}*/
