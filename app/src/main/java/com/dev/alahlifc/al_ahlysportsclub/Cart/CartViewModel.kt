package com.dev.alahlifc.al_ahlysportsclub.Cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class CartViewModel() : ViewModel() {
    private val cartRepository =  CartRepository()
    var result = MutableLiveData<Resource<mCart>>()


     fun Cart(
         access_token : String
    ): MutableLiveData<Resource<mCart>> {

         if (result.value==null)
        result = cartRepository.Cart(access_token)
        return result
    }


    var result2 = MutableLiveData<Resource<mDeleteCart>>()
    fun DeleteCart(
        access_token : String,
        link: String,
        lang: String
    ): MutableLiveData<Resource<mDeleteCart>> {

        // if (result.value==null)
        result2 = cartRepository.DeleteCart(
            access_token ,
            link,
            lang
        )
        return result2
    }


    var result3 = MutableLiveData<Resource<mCheckout>>()
    fun Checkout(
        access_token : String,
        lang: String
    ): MutableLiveData<Resource<mCheckout>> {

        // if (result.value==null)
        result3 = cartRepository.Checkout(
            access_token ,
            lang
        )
        return result3
    }




}
