package com.dev.alahlifc.al_ahlysportsclub.NewestProducts.bottombuy

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.szModel
import kotlinx.android.synthetic.main.row_select_size.view.*


class SelectNColorAdapter(private val mModelList: List<szModel>?) :
    RecyclerView.Adapter<SelectNColorAdapter.MyViewHolder>() {

    var previousPosition: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_select_size, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = mModelList!![position]
        holder.itemView.sz_tv.text = model.text
       // holder.itemView.v.setBackgroundColor(if (model.isSelected) Color.parseColor("#008640")else Color.WHITE)

        holder.itemView.sz_tv.setTextColor(if (model.isSelected) Color.parseColor("#008640") else Color.parseColor("#959595"))
        holder.itemView.sz_tv.background = if (model.isSelected) holder.itemView.context.getDrawable(R.drawable.square_border_color_primary)
                                                else holder.itemView.context.getDrawable(R.drawable.square_border)
        holder.itemView.sz_tv.setOnClickListener {
            if (model.isSelected){return@setOnClickListener}

            model.isSelected = !model.isSelected
            //  if (mModelList.filter { it.isSelected }.isEmpty()) previousPosition = -1

            if (previousPosition != -1 && previousPosition != position) {
                mModelList.get(previousPosition).isSelected = false
                previousPosition = position
            } else {
                previousPosition = position
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mModelList?.size ?: 0
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}