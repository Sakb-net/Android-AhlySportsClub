package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo

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

class CheckoutUiRepository {

    fun ResultCallback(
        access_token : String,
        checkout_id  : String,
        lang: String
    ) : MutableLiveData<Resource<mResultCallback>> {
        val result = MutableLiveData<Resource<mResultCallback>>()
        result.value = Resource.loading(true)
        Api().ResultCallback(
            access_token ,
            checkout_id,
            lang

        ).enqueue(object : Callback<mResultCallback>{
            override fun onResponse(call: Call<mResultCallback>, response: Response<mResultCallback>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mResultCallback>() {}.type
                    val errorResponse: mResultCallback = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mResultCallback>, t: Throwable) {
                Timber.e("err "+t.printStackTrace())
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}