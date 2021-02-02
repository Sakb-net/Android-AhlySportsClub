package com.dev.alahlifc.al_ahlysportsclub.Cart

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

class CartRepository {


    fun Cart(
        access_token : String
       ) : MutableLiveData<Resource<mCart>> {
        val result = MutableLiveData<Resource<mCart>>()
        result.value = Resource.loading(true)
        Api().getCart(
            access_token
           ).enqueue(object : Callback<mCart>{
            override fun onResponse(call: Call<mCart>, response: Response<mCart>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mCart>() {}.type
                    val errorResponse: mCart = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mCart>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }





    fun DeleteCart(
        access_token : String,
        link: String,
        lang: String
    ) : MutableLiveData<Resource<mDeleteCart>> {
        val result = MutableLiveData<Resource<mDeleteCart>>()
        result.value = Resource.loading(true)
        Api().DeleteCart(
            access_token ,
            link,
            lang

        ).enqueue(object : Callback<mDeleteCart>{
            override fun onResponse(call: Call<mDeleteCart>, response: Response<mDeleteCart>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mDeleteCart>() {}.type
                    val errorResponse: mDeleteCart = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mDeleteCart>, t: Throwable) {
                Timber.e("err "+t.printStackTrace())
                result.value = Resource.failure(t)
            }
        })

        return result
    }


    fun Checkout(
        access_token : String,
        lang: String
    ) : MutableLiveData<Resource<mCheckout>> {
        val result = MutableLiveData<Resource<mCheckout>>()
        result.value = Resource.loading(true)
        Api().Checkout(
            access_token ,
            lang

        ).enqueue(object : Callback<mCheckout>{
            override fun onResponse(call: Call<mCheckout>, response: Response<mCheckout>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mCheckout>() {}.type
                    val errorResponse: mCheckout = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mCheckout>, t: Throwable) {
                Timber.e("err "+t.printStackTrace())
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}