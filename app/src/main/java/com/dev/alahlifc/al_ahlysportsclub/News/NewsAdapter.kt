package com.dev.alahlifc.al_ahlysportsclub.News

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemNewsBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mNews


class NewsAdapter(private val mutableList: MutableList<mNews.Data?>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2

    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Any) {
            binding.newsData = data as mNews.Data
            binding.executePendingBindings ();

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemNewsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_news, parent, false)

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

    fun addMoreItems(data: List<mNews.Data?>) {
        mutableList.addAll(mutableList.size,data)
        notifyItemInserted(mutableList.size)
    }

    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
            holder.bind(mutableList[position]!!)
    }





}