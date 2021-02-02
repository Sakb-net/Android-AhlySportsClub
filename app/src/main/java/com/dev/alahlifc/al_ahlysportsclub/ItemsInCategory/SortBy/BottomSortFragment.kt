package com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory.SortBy


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentBottomSortBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSortFragment(): BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomSortBinding
     var onItemClick: ((position : Int) -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_bottom_sort, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeViewsFonts()
       /* binding.newest.setOnClickListener {
            onItemClick?.invoke(1)
            dismiss()
        }*/
        binding.bestSelling.setOnClickListener {
            onItemClick?.invoke(2)
            dismiss()
        }
        binding.fromToToLow.setOnClickListener {
            onItemClick?.invoke(3)
            dismiss()
        }
        binding.fromBotomToTop.setOnClickListener {
            onItemClick?.invoke(4)
            dismiss()
        }
    }
    private fun changeViewsFonts() {
        Util.changeViewTypeFace(context,
            Constants.FONT_REGULAR,
           /* binding.newest,*/binding.bestSelling,binding.fromBotomToTop,binding.fromToToLow, binding.positiveBtn)



    }


}
