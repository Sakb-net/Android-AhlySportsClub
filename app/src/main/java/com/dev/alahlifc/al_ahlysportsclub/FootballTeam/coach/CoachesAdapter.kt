package com.dev.alahlifc.al_ahlysportsclub.FootballTeam.coach

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ItemFootballcoachBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mFootballTeam


class CoachesAdapter(private val mutableList: MutableList<mFootballTeam.Coache>) :
    RecyclerView.Adapter<CoachesAdapter.ViewHolder>() {

    var onItemClick: ((position : Int) -> Unit)? = null

    inner class ViewHolder(val binding: ItemFootballcoachBinding) : RecyclerView.ViewHolder(binding.root) {

init {
    binding.root.setOnClickListener {
        onItemClick?.invoke(adapterPosition)
    }

    changeViewsFonts()
}
        fun bind(data: Any) {
            binding.footballData = data as mFootballTeam.Coache
        }

        private fun changeViewsFonts() {
            Util.changeViewTypeFace(binding.root.context,
                Constants.FONT_REGULAR,
                binding.categoryTitle)



        }

    }

    fun updateDataset(data : MutableList<mFootballTeam.Coache>){
        mutableList.clear()
        mutableList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding: ItemFootballcoachBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_footballcoach, parent, false)

    return  ViewHolder(binding)
    }


    override fun getItemCount(): Int = mutableList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {
            holder.bind(mutableList[position])
    }





}