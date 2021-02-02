package com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemCategoriesItemsBinding
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemLoadingBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mItemsInCategory

class ItemsInCategoryPagingAdapter (context : Context): PagedListAdapter<mItemsInCategory.Product, RecyclerView.ViewHolder>(SHOW_COMPARATOR) {

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
        val binding: ItemCategoriesItemsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_categories_items, parent, false)
        val bindingloading: ItemLoadingBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_loading, parent, false)

     return when (viewType) {
            ITEM_VIEW_TYPE -> ShowItemViewHolder(binding)
            NETWORK_STATE_VIEW_TYPE -> NetworkLoadingViewHolder(bindingloading)
            else -> throw IllegalStateException()
        }


    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_VIEW_TYPE -> (holder as ShowItemViewHolder).bind(getItem(position)!!)
            NETWORK_STATE_VIEW_TYPE -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (isPaginating() && position == itemCount - 1) NETWORK_STATE_VIEW_TYPE else ITEM_VIEW_TYPE

    override fun getItemCount(): Int = super.getItemCount() + if (isPaginating()) 1 else 0

    private fun isPaginating(): Boolean = networkStatus != null && networkStatus!! == NetworkStatus.LOADING

    inner class ShowItemViewHolder(val binding: ItemCategoriesItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            changeViewsFonts()
            }

        fun bind(data: Any) {
            binding.categoriesItemsData = data as mItemsInCategory.Product
            binding.root.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }

        }

        private fun changeViewsFonts() {
            Util.changeViewTypeFace(binding.root.context,
                Constants.FONT_REGULAR,
                binding.categoryTitle, binding.catPrice)



        }
    }
    class NetworkLoadingViewHolder(val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val ITEM_VIEW_TYPE = R.layout.item_categories_items
        const val NETWORK_STATE_VIEW_TYPE = R.layout.item_loading

        private val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<mItemsInCategory.Product>() {
            override fun areItemsTheSame(oldItem: mItemsInCategory.Product, newItem:mItemsInCategory.Product): Boolean =
                oldItem.link == newItem.link

            override fun areContentsTheSame(oldItem: mItemsInCategory.Product, newItem:mItemsInCategory.Product): Boolean =
                oldItem == newItem
        }
    }
}