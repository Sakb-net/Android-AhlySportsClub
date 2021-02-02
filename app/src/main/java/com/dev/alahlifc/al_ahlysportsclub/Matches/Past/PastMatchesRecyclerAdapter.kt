package com.dev.alahlifc.al_ahlysportsclub.Matches.Past

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemPastMatchBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches
import timber.log.Timber


class PastMatchesRecyclerAdapter :
    RecyclerView.Adapter<PastMatchesRecyclerAdapter.ViewHolder>() {


    var onItemClick: ((position : Int) -> Unit)? = null

    var mutableList :  MutableList<mMatches.Data>? = mutableListOf()
    fun setData( data : MutableList<mMatches.Data>?){

        mutableList = data
        notifyDataSetChanged()
    }
  inner class ViewHolder(val binding: ItemPastMatchBinding) : RecyclerView.ViewHolder(binding.root) {


      init {
         /// changeViewsFonts()
          binding.textView151.setOnClickListener {
              onItemClick?.invoke(adapterPosition)
          }
      }


        fun bind(data: Any) {
            binding.matchData = data as mMatches.Data
            binding.executePendingBindings()
//            with(itemView) {
//                team1_tv.text = ""+data.firstTeam
//            }
        }

      private fun changeViewsFonts() {
          Util.changeViewTypeFace(binding.root.context,
              Constants.FONT_REGULAR
              ,
              binding.textView15, binding.textView151, binding.team1Tv, binding.team2Tv)
      }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemPastMatchBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_past_match, parent, false)


        return  ViewHolder(binding)



    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = mutableList!!.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
      //  if(mutableList[position].ads.size>1)
        //Timber.e(""+position)
        holder.bind(mutableList!![position])
    }





}