package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class BasePaymentViewModel() : ViewModel() {
    private val checkoutUiRepository =  CheckoutUiRepository()


    var result3 = MutableLiveData<Resource<mResultCallback>>()
    fun ResultCallBackCheck(
        access_token : String,
        checkout_id : String,
        lang: String
    ): MutableLiveData<Resource<mResultCallback>> {

        // if (result.value==null)
        result3 = checkoutUiRepository.ResultCallback(
            access_token ,
            checkout_id,
            lang
        )
        return result3
    }




}
