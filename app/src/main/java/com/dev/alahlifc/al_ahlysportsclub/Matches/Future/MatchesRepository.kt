package com.dev.alahlifc.al_ahlysportsclub.Matches.Future

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesRepository {


    fun Matches(
   lang: String,
       limit: String,
        num_page: String,
        type : String
       ) : MutableLiveData<Resource<mMatches>> {
        val result = MutableLiveData<Resource<mMatches>>()
        result.value = Resource.loading(true)
        Api().getMatches(

            lang,
            limit,
            num_page,
            type
           ).enqueue(object : Callback<mMatches>{
            override fun onResponse(call: Call<mMatches>, response: Response<mMatches>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mMatches>() {}.type
                    val errorResponse: mMatches = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mMatches>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}