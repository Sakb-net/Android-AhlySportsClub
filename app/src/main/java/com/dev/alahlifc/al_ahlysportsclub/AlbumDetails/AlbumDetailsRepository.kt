package com.dev.alahlifc.al_ahlysportsclub.AlbumDetails

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailsRepository {


    fun SubAlbums(
      lang: String,
      albums_link : String
       ) : MutableLiveData<Resource<mSubAlbum>> {
        val result = MutableLiveData<Resource<mSubAlbum>>()
        result.value = Resource.loading(true)
        Api().SubAlbum(
            lang,
            albums_link
           ).enqueue(object : Callback<mSubAlbum>{
            override fun onResponse(call: Call<mSubAlbum>, response: Response<mSubAlbum>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mSubAlbum>() {}.type
                    val errorResponse: mSubAlbum = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mSubAlbum>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}