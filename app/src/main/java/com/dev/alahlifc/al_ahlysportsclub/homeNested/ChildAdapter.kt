package com.dev.alahlifc.al_ahlysportsclub.homeNested

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.loadImage
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import kotlinx.android.synthetic.main.item_horizontal.view.*

class ChildAdapter(private var children: List<Any>?)
    : RecyclerView.Adapter<ChildAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal,parent,false)
        v.run {

            Util.changeViewTypeFace(parent.context, Constants.FONT_REGULAR,
                glorious_number )


        }
        return ViewHolder(v)
    }

   fun updateData( child: List<Any>?){
        children = child
       notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return children?.size ?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(this!!.children!!.get(position)!!)
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindViewHolder(item: Any) {
            if (item is mHome.Album) {
                itemView.glorious_number.text = (item as mHome.Album).name.toString()
                itemView.categoryIv.loadImage(Constants.baseUrl+(item as mHome.Album).image.toString(),230,136)
            }
            else if (item is mHome.New) {
                itemView.glorious_number.text = (item as mHome.New).name.toString()
                itemView.categoryIv.loadImage(Constants.baseUrl+(item as mHome.New).image.toString(),230,136)
            }
            else if (item is mHome.Video){
                itemView.glorious_number.text = (item as mHome.Video).name.toString()
                itemView.categoryIv.loadImage(Constants.baseUrl+(item as mHome.Video).image.toString(),230,136)
            }

            else if (item is mHome.Product){
                itemView.glorious_number.text = (item as mHome.Product).name.toString()
                itemView.categoryIv.loadImage(Constants.baseUrl+(item as mHome.Product).image.toString(),230,136)
            }

        }

    }
}