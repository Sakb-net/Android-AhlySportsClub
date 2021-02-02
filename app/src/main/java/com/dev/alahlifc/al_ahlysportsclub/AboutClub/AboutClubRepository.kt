package com.dev.alahlifc.al_ahlysportsclub.AboutClub

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAbout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutClubRepository {
    fun about() : MutableLiveData<Resource<mAbout>> {
        val result = MutableLiveData<Resource<mAbout>>()
        result.value = Resource.loading(true)
        Api().about(
           ).enqueue(object : Callback<mAbout>{
            override fun onResponse(call: Call<mAbout>, response: Response<mAbout>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mAbout>() {}.type
                    val errorResponse: mAbout = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mAbout>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }

}