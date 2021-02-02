package com.dev.alahlifc.al_ahlysportsclub.ProductDetails

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAbout
import com.dev.alahlifc.al_ahlysportsclub.models.mProductSingle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsRepository {
    fun product( lang: String, products_link  : String) : MutableLiveData<Resource<mProductSingle>> {
        val result = MutableLiveData<Resource<mProductSingle>>()
        result.value = Resource.loading(true)
        Api().SingleProduct(
            lang,
           products_link
           ).enqueue(object : Callback<mProductSingle>{
            override fun onResponse(call: Call<mProductSingle>, response: Response<mProductSingle>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mProductSingle>() {}.type
                    val errorResponse: mProductSingle = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mProductSingle>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }

}