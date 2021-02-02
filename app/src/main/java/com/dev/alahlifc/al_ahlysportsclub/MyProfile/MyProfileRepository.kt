package com.dev.alahlifc.al_ahlysportsclub.MyProfile

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mMyProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileRepository {
    fun fillMyProfile(access_token : String) : MutableLiveData<Resource<mMyProfile>> {
        val result = MutableLiveData<Resource<mMyProfile>>()
        result.value = Resource.loading(true)
        Api().myProfile(access_token
           ).enqueue(object : Callback<mMyProfile>{
            override fun onResponse(call: Call<mMyProfile>, response: Response<mMyProfile>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mMyProfile>() {}.type
                    val errorResponse: mMyProfile = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mMyProfile>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }

}