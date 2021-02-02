package com.dev.alahlifc.al_ahlysportsclub.LogOut

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mLogout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutRepository {



    fun logout(
        access_token : String) : MutableLiveData<Resource<mLogout>> {
        val result = MutableLiveData<Resource<mLogout>>()
        result.value = Resource.loading(true)
        Api().logout(
            access_token
        ).enqueue(object : Callback<mLogout>{
            override fun onResponse(call: Call<mLogout>, response: Response<mLogout>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mLogout>() {}.type
                    val errorResponse: mLogout = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mLogout>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}