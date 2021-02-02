package com.dev.alahlifc.al_ahlysportsclub.Album

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemAlbumBinding
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemLoadingBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mAlbum

class AlbumPagingAdapter (context : Context): PagedListAdapter<mAlbum.Data, RecyclerView.ViewHolder>(SHOW_COMPARATOR) {

    val context = context
    var onItemClick: ((position : Int) -> Unit)? = null

    var networkStatus: NetworkStatus? = null
        set(networkStatus) {
            this.networkStatus.let {
                field = networkStatus
                when {
                    it == NetworkStatus.LOADING && networkStatus != NetworkStatus.LOADING ->
                        notifyItemRemoved(super.getItemCount())

                    it != NetworkStatus.LOADING && networkStatus == NetworkStatus.LOADING ->
                        notifyItemInserted(super.getItemCount())
                }
            }
        }

    /*



     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemAlbumBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_album, parent, false)
        val bindingloading: ItemLoadingBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_loading, parent, false)

     return when (viewType) {
            ITEM_VIEW_TYPE -> ShowItemViewHolder(binding)
            NETWORK_STATE_VIEW_TYPE -> NetworkLoadingViewHolder(bindingloading)
            else -> throw IllegalStateException()
        }

     /* .inflate(viewType, parent, false).let {
            when (viewType) {
                ITEM_VIEW_TYPE -> ShowItemViewHolder(it)
                NETWORK_STATE_VIEW_TYPE -> NetworkLoadingViewHolder(it)
                else -> throw IllegalStateException()
            }
        }*/
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_VIEW_TYPE -> (holder as ShowItemViewHolder).bind(getItem(position))
            NETWORK_STATE_VIEW_TYPE -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (isPaginating() && position == itemCount - 1) NETWORK_STATE_VIEW_TYPE else ITEM_VIEW_TYPE

    override fun getItemCount(): Int = super.getItemCount() + if (isPaginating()) 1 else 0

    private fun isPaginating(): Boolean = networkStatus != null && networkStatus!! == NetworkStatus.LOADING

    inner class ShowItemViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
        fun bind(item : mAlbum.Data?) {
            binding.albumData = item as mAlbum.Data
        }
    }
    class NetworkLoadingViewHolder(val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val ITEM_VIEW_TYPE = R.layout.item_news
        const val NETWORK_STATE_VIEW_TYPE = R.layout.item_loading

        private val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<mAlbum.Data>() {
            override fun areItemsTheSame(oldItem: mAlbum.Data, newItem: mAlbum.Data): Boolean =
                oldItem.link == newItem.link

            override fun areContentsTheSame(oldItem: mAlbum.Data, newItem: mAlbum.Data): Boolean =
                oldItem == newItem
        }
    }
}