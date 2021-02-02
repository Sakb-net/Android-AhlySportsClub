package com.dev.alahlifc.al_ahlysportsclub.Matches.Future

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemNextMatchBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches


class MatchesRecyclerAdapter :
    RecyclerView.Adapter<MatchesRecyclerAdapter.ViewHolder>() {

    var mutableList :  MutableList<mMatches.Data>? = mutableListOf()
   fun setData( data : MutableList<mMatches.Data>?){

       mutableList = data
       notifyDataSetChanged()
   }

   inner class ViewHolder(val binding: ItemNextMatchBinding) : RecyclerView.ViewHolder(binding.root) {


       init {
         //  changeViewsFonts()

       }


       private fun changeViewsFonts() {
           Util.changeViewTypeFace(binding.root.context,
               Constants.FONT_REGULAR
               ,
               binding.textView15, binding.team1Tv, binding.team2Tv)
       }


        fun bind(data: Any) {

            binding.matchData = data as mMatches.Data
            binding.executePendingBindings()
           // with(itemView) {
             //   team1_tv.text = ""+data.firstTeam
          //  }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemNextMatchBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_next_match, parent, false)


      return  ViewHolder(binding)


    }

    override fun getItemCount(): Int = mutableList!!.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
      //  if(mutableList[position].ads.size>1)
            holder.bind(mutableList!![position])
    }





}