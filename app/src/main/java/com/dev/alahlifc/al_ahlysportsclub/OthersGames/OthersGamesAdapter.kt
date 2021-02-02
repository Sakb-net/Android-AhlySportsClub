package com.dev.alahlifc.al_ahlysportsclub.OthersGames

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemOthersGamesBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mTeams


class OthersGamesAdapter(private val mutableList: MutableList<mTeams.Data>) :
    RecyclerView.Adapter<OthersGamesAdapter.ViewHolder>() {

    var onItemClick: ((positon:Int) -> Unit)? = null
   inner class ViewHolder(val binding: ItemOthersGamesBinding) : RecyclerView.ViewHolder(binding.root) {

init {
    changeViewsFonts()
    binding.root.setOnClickListener {
        onItemClick?.invoke(adapterPosition)
    }
}
        fun bind(data: Any) {
            binding.data = data as mTeams.Data
        }

        private fun changeViewsFonts() {
            Util.changeViewTypeFace(binding.root.context,
                Constants.FONT_REGULAR,
                binding.gameNameTv)



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemOthersGamesBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_others_games, parent, false)

    return  ViewHolder(binding)
    }


    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
            holder.bind(mutableList[position])
    }





}