//package com.dev.mahmoud_ashraf.al_ahlysportsclub.Album
//
//import androidx.lifecycle.MutableLiveData
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.Reset.Api
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.Reset.Resource
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mAlbum
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mNews
//import com.dev.mahmoud_ashraf.al_ahlysportsclub.models.mVideos
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class AlbumsRepository {
//    fun getAlbums(num_page: String, limit: String, lang : String) : MutableLiveData<Resource<mAlbum>> {
//        val result = MutableLiveData<Resource<mAlbum>>()
//        result.value = Resource.loading(true)
//        Api().albums(num_page,limit,lang)
//            .enqueue(object : Callback<mAlbum>{
//            override fun onResponse(call: Call<mAlbum>, response: Response<mAlbum>) {
//                if (response.isSuccessful) {
//                    result.value = Resource.loading(false)
//                    result.value = Resource.success(response.body()!!)
//                }else{
//                    result.value = Resource.loading(false)
//                    val gson = Gson()
//                    val type = object : TypeToken<mAlbum>() {}.type
//                    val errorResponse: mAlbum = gson.fromJson(response.errorBody()!!.charStream(), type)
//                    result.value = Resource.success(errorResponse)
//                    //Timber.e(" "+ response.errorBody()!!.string())
//
//                }
//            }
//
//            override fun onFailure(call: Call<mAlbum>, t: Throwable) {
//                result.value = Resource.failure(t)
//            }
//        })
//
//        return result
//    }
//
//}