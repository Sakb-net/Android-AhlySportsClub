package com.dev.alahlifc.al_ahlysportsclub.Audience

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AudienceRepository {


    fun Audience(
   lang: String
       ) : MutableLiveData<Resource<mAudience>> {
        val result = MutableLiveData<Resource<mAudience>>()
        result.value = Resource.loading(true)
        Api().Audience(
            lang
           ).enqueue(object : Callback<mAudience>{
            override fun onResponse(call: Call<mAudience>, response: Response<mAudience>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mAudience>() {}.type
                    val errorResponse: mAudience = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mAudience>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}