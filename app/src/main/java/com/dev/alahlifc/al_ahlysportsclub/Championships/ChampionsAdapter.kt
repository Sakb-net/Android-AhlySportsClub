package com.dev.alahlifc.al_ahlysportsclub.Championships

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemChampionsBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mChampions


class ChampionsAdapter(private val mutableList: MutableList<mChampions.DataX?>) :
    RecyclerView.Adapter<ChampionsAdapter.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null


    inner class ViewHolder(val binding: ItemChampionsBinding) : RecyclerView.ViewHolder(binding.root) {
init {

}

        fun bind(data: Any) {
            binding.championsData = data as mChampions.DataX
            binding.executePendingBindings()
//            binding.root.setOnClickListener {
//                onItemClick?.invoke(adapterPosition)
//
//            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemChampionsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_champions, parent, false)

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

    fun addMoreItems(data: List<mChampions.DataX?>) {
        mutableList.addAll(mutableList.size,data)
        notifyItemInserted(mutableList.size)
    }

    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
        holder.bind(mutableList[position]!!)

    }





}