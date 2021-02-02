package com.dev.alahlifc.al_ahlysportsclub.Categories

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mProductCategories
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesRepository {


    fun Categories(
       access_token : String,
   lang: String,
       limit: String,
        num_page: String
       ) : MutableLiveData<Resource<mProductCategories>> {
        val result = MutableLiveData<Resource<mProductCategories>>()
        result.value = Resource.loading(true)
        Api().ProductCategories(
            access_token,
            lang,
            limit,
            num_page
           ).enqueue(object : Callback<mProductCategories>{
            override fun onResponse(call: Call<mProductCategories>, response: Response<mProductCategories>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mProductCategories>() {}.type
                    val errorResponse: mProductCategories = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mProductCategories>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}