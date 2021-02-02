package com.dev.alahlifc.al_ahlysportsclub.AlbumDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemAlbumDetailsBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mSubAlbum


class AlbumDetailsAdapter(private val mutableList: MutableList<mSubAlbum.SubAlbum?>) :
    RecyclerView.Adapter<AlbumDetailsAdapter.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null


    inner class ViewHolder(val binding: ItemAlbumDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
init {
                binding.root.setOnClickListener {
                       onItemClick?.invoke(adapterPosition)

           }
}

        fun bind(data: Any) {
            binding.subAlbumData = data as mSubAlbum.SubAlbum
//            binding.root.setOnClickListener {
//                onItemClick?.invoke(adapterPosition)
//
//            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemAlbumDetailsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_album_details, parent, false)

        return  ViewHolder(binding)
    }


    override fun getItemViewType(position: Int): Int {
        return if (mutableList.get(position) != null)
            VIEW_TYPE_ITEM
        else
            VIEW_TYPE_LOADING
    }

    fun addNullData() {
        mutableList.add(mutableList.size,null)
        notifyItemInserted(mutableList.size)
    }

    fun removeNull() {
        mutableList.removeAt(mutableList.size-1)
        notifyItemRemoved(mutableList.size-1)
    }

    fun addMoreItems(data: List<mSubAlbum.SubAlbum?>) {
        mutableList.addAll(mutableList.size,data)
        notifyItemInserted(mutableList.size)
    }

    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
        holder.bind(mutableList[position]!!)

    }





}