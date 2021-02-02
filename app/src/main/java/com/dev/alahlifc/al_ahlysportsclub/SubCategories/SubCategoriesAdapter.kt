package com.dev.alahlifc.al_ahlysportsclub.SubCategories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemSubcategoriesBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mProductCategories


class SubCategoriesAdapter(private val mutableList: MutableList<mProductCategories.Subcategory?>) :
    RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null


    inner class ViewHolder(val binding: ItemSubcategoriesBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Any) {
            binding.subCategoriesData = data as mProductCategories.Subcategory
            binding.root.setOnClickListener {
                onItemClick?.invoke(adapterPosition)

            }
            changeViewsFonts()
        }

       private fun changeViewsFonts() {
           Util.changeViewTypeFace(binding.root.context,
               Constants.FONT_REGULAR,
               binding.categoryTitle)



       }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemSubcategoriesBinding  =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_subcategories, parent, false)

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

    fun addMoreItems(data: List<mProductCategories.Subcategory?>) {
        mutableList.addAll(mutableList.size,data)
        notifyItemInserted(mutableList.size)
    }

    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
            holder.bind(mutableList[position]!!)

    }





}