package com.dev.alahlifc.al_ahlysportsclub.Calendar

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarRepository {


    fun Calendar(
      lang: String
       ) : MutableLiveData<Resource<mCalendar>> {
        val result = MutableLiveData<Resource<mCalendar>>()
        result.value = Resource.loading(true)
        Api().Calendar(
            lang
           ).enqueue(object : Callback<mCalendar>{
            override fun onResponse(call: Call<mCalendar>, response: Response<mCalendar>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mCalendar>() {}.type
                    val errorResponse: mCalendar = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mCalendar>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}