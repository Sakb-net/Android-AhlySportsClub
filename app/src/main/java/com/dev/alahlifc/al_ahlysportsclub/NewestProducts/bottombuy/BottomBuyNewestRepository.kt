package com.dev.alahlifc.al_ahlysportsclub.NewestProducts.bottombuy

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class BottomBuyNewestRepository {


    fun AddUpdateCart(
        access_token : String,
        link: String,
        quantity: String,
        weight: String,
        color: String,
        name_print: String,
        fees : MutableList<String>,
        lang: String
       ) : MutableLiveData<Resource<mAddUpdateCart>> {
        val result = MutableLiveData<Resource<mAddUpdateCart>>()
        result.value = Resource.loading(true)
        Api().AddUpdateCart(
             access_token ,
             link,
            quantity,
            weight,
            color,
            name_print,
            fees ,
            lang


           ).enqueue(object : Callback<mAddUpdateCart>{
            override fun onResponse(call: Call<mAddUpdateCart>, response: Response<mAddUpdateCart>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mAddUpdateCart>() {}.type
                    val errorResponse: mAddUpdateCart = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mAddUpdateCart>, t: Throwable) {
                Timber.e("err "+t.printStackTrace())
                result.value = Resource.failure(t)
            }
        })

        return result
    }










}