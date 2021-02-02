package com.dev.alahlifc.al_ahlysportsclub.ProductDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAbout
import com.dev.alahlifc.al_ahlysportsclub.models.mProductSingle

class ProductDetailsViewModel() : ViewModel() {
    private val productDetailsRepository =  ProductDetailsRepository()
    var result = MutableLiveData<Resource<mProductSingle>>()

    fun productDetails(lang: String, products_link  : String) : MutableLiveData<Resource<mProductSingle>> {

        if(result.value == null) {
            result = productDetailsRepository.product(lang, products_link )
        }

        return result
    }
}
