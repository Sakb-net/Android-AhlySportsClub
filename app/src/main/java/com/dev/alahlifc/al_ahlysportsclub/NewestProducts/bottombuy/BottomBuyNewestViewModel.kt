package com.dev.alahlifc.al_ahlysportsclub.NewestProducts.bottombuy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class BottomBuyNewestViewModel() : ViewModel() {
    private val bottomButRepository =  BottomBuyNewestRepository()
    var result = MutableLiveData<Resource<mAddUpdateCart>>()



     fun AddUpdateCart(
         access_token : String,
         link: String,
         quantity: String,
         weight: String,
         color: String,
         name_print: String,
         fees : MutableList<String>,
         lang: String
    ): MutableLiveData<Resource<mAddUpdateCart>> {

        // if (result.value==null)
        result = bottomButRepository.AddUpdateCart(
            access_token ,
            link,
            quantity,
            weight,
            color,
            name_print,
            fees ,
            lang
        )
        return result
    }





}
