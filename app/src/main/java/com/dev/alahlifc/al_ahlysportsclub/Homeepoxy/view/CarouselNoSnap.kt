package com.dev.alahlifc.al_ahlysportsclub.Homeepoxy.view

import android.content.Context
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CarouselNoSnap(context: Context) : Carousel(context) {

    override fun getSnapHelperFactory(): Nothing? = null
}