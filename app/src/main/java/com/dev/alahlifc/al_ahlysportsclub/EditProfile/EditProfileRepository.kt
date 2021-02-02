package com.dev.alahlifc.al_ahlysportsclub.EditProfile

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mEditProfile
import com.dev.alahlifc.al_ahlysportsclub.models.mUploadImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class EditProfileRepository {

    var call : Call<mUploadImage> ? = null

    fun editProfile(
        access_token : String,
        email: String,
        display_name: String,
        phone : String,
        image: String,
        country: String,
        city : String,
        state: String,
        gender: String) : MutableLiveData<Resource<mEditProfile>> {
        val result = MutableLiveData<Resource<mEditProfile>>()
        result.value = Resource.loading(true)
        Api().editProfile(

          access_token, email, display_name,
            phone, image, country,
            city, state, gender

           ).enqueue(object : Callback<mEditProfile>{
            override fun onResponse(call: Call<mEditProfile>, response: Response<mEditProfile>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mEditProfile>() {}.type
                    val errorResponse: mEditProfile = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mEditProfile>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }


    fun uploadImage(access_token : String, image: String) : MutableLiveData<Resource<mUploadImage>> {
        val result = MutableLiveData<Resource<mUploadImage>>()
        result.value = Resource.loading(true)
      call =   Api().uploadImage(access_token,  image)

          call?.enqueue(object : Callback<mUploadImage>{
            override fun onResponse(call: Call<mUploadImage>, response: Response<mUploadImage>) {
                if (response.isSuccessful) {
                    Timber.e("dddddddddddddddddddddddddddddd "+response.body())

                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mUploadImage>() {}.type
                    val errorResponse: mUploadImage = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mUploadImage>, t: Throwable) {
                if(!call.isCanceled)
                result.value = Resource.failure(t)
                else
                    result.value = Resource.failure(Throwable("cancelled"))
            }
        })

        return result
    }

    fun cancelUploading(){
        call?.cancel()
    }

}