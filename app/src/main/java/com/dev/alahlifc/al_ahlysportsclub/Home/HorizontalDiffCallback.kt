package com.dev.alahlifc.al_ahlysportsclub.Home

import androidx.recyclerview.widget.DiffUtil

object HorizontalDiffCallback : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem:Any, newItem: Any) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
}