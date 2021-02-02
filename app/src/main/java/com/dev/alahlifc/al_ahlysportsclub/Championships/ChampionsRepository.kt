package com.dev.alahlifc.al_ahlysportsclub.Championships

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mChampions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChampionsRepository {


    fun Champions(
   lang: String
       ) : MutableLiveData<Resource<mChampions>> {
        val result = MutableLiveData<Resource<mChampions>>()
        result.value = Resource.loading(true)
        Api().Champions(
            lang
           ).enqueue(object : Callback<mChampions>{
            override fun onResponse(call: Call<mChampions>, response: Response<mChampions>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mChampions>() {}.type
                    val errorResponse: mChampions = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mChampions>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}