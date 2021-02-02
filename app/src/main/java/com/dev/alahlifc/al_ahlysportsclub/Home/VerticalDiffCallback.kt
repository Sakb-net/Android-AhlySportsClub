package com.dev.alahlifc.al_ahlysportsclub.Home

import androidx.recyclerview.widget.DiffUtil
import com.dev.alahlifc.al_ahlysportsclub.models.mHome

object VerticalDiffCallback : DiffUtil.ItemCallback<Pair<String,mHome.Data?>?>() {
    override fun areItemsTheSame(oldItem: Pair<String, mHome.Data?>, newItem: Pair<String, mHome.Data?>): Boolean {
      return  oldItem?.second == newItem?.second
    }

    override fun areContentsTheSame(oldItem: Pair<String, mHome.Data?>, newItem: Pair<String, mHome.Data?>): Boolean {
       return oldItem?.second == newItem?.second
    }

}