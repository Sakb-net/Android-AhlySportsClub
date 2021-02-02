package com.dev.alahlifc.al_ahlysportsclub.Base

import android.content.Context
import android.widget.ArrayAdapter


class spinnerHelperAdapter(theContext: Context, objects: List<Any>, theLayoutResId: Int) :
    ArrayAdapter<Any>(theContext, theLayoutResId, objects) {

    override fun getCount(): Int {
        // don't display last item. It is used as hint.
        val count = super.getCount()
        return if (count > 0) count - 1 else count
    }
}