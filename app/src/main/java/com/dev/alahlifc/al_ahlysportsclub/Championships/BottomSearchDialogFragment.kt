package com.dev.alahlifc.al_ahlysportsclub.Championships

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.alahlifc.al_ahlysportsclub.R;

class BottomSearchDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.botom_search_dialog, container, false)
    }

}
