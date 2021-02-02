package com.dev.alahlifc.al_ahlysportsclub.FootballTeam.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemFootballplayerBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mFootballTeam


class PlayersAdapter(private val mutableList: MutableList<mFootballTeam.Player>) :
    RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    var onItemClick: ((position : Int) -> Unit)? = null
   inner class ViewHolder(val binding: ItemFootballplayerBinding) : RecyclerView.ViewHolder(binding.root) {

init {
    changeViewsFonts()
    binding.root.setOnClickListener {
        onItemClick?.invoke(adapterPosition)
    }
}
        fun bind(data: Any) {
            binding.footballData = data as mFootballTeam.Player
        }

        private fun changeViewsFonts() {
            Util.changeViewTypeFace(binding.root.context,
                Constants.FONT_REGULAR,
                binding.categoryTitle)



        }

    }

    fun updateDataset(data : MutableList<mFootballTeam.Player>){
        mutableList.clear()
        mutableList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemFootballplayerBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_footballplayer, parent, false)

    return ViewHolder(binding)
    }


    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
            holder.bind(mutableList[position])
    }





}