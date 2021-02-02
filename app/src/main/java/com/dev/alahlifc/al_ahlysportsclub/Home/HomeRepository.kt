package com.dev.alahlifc.al_ahlysportsclub.Home

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    fun home(lang : String , limit : String) : MutableLiveData<Resource<mHome>> {
        val result = MutableLiveData<Resource<mHome>>()
        result.value = Resource.loading(true)
        Api().fillHome(lang ,limit).enqueue(object : Callback<mHome> {
            override fun onResponse(call: Call<mHome>, response: Response<mHome>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mHome>() {}.type
                    val errorResponse: mHome = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mHome>, t: Throwable) {
                t.printStackTrace()
                result.value = Resource.failure(t)
            }
        })

        return result
    }

   /**dummy data ...
    *  private val _verticalData = mutableListOf<List<Int>>()
    private val _horizontalData = mutableListOf<Int>()

    val verticalData: List<List<Int>> get() = _verticalData

    init {
        for (i in 1..100) {
            _horizontalData.add(i)
        }

        for (i in 1..25) {
            _verticalData.add(_horizontalData.toList())
        }
    }*/
}