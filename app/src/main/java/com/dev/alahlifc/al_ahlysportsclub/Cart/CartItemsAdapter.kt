package com.dev.alahlifc.al_ahlysportsclub.Cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemCartBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mCart


class CartItemsAdapter(private val mutableList: MutableList<mCart.ProductCart?>) :
    RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    private  val VIEW_TYPE_ITEM = 1
    private  val VIEW_TYPE_LOADING = 2
    var onItemClick: ((position : Int) -> Unit)? = null


    inner class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(data: Any) {
            binding.productCartData = data as mCart.ProductCart



            if (data.fees.size==0){binding.cartItemFee.visibility = View.GONE}
            else{binding.cartItemFee.visibility = View.VISIBLE}
            data.fees.map  {
                if(it.typePrice=="value") {
                    binding.cartItemFee.text = "" + binding.cartItemFee.text + "" + it.name + "   " + it.price + " " +
                            (binding.root.context).getString(R.string.rs) + "\n"
                }
                else
                    binding.cartItemFee.text = ""+ binding.cartItemFee.text+""+it.name+"   "+it.price+" % \n"

            }

            binding.btnRemove.setOnClickListener {
                onItemClick?.invoke(adapterPosition)

            }
            changeViewsFonts()
        }

       private fun changeViewsFonts() {
           Util.changeViewTypeFace(binding.root.context,
               Constants.FONT_REGULAR,
               binding.cartItemTitle,
               binding.priceProductTv,
               binding.discProductTv,
               binding.cartItemQuantity,
               binding.cartItemFee,
               binding.catPrice,
               binding.btnRemove
               )



       }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemCartBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false)

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
    fun removeAt(position: Int) {
        mutableList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addMoreItems(data: List<mCart.ProductCart?>) {
        mutableList.addAll(mutableList.size,data)
        notifyItemInserted(mutableList.size)
    }

    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
            holder.bind(mutableList[position]!!)

    }





}