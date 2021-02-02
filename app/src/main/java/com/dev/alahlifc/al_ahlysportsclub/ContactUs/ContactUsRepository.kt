package com.dev.alahlifc.al_ahlysportsclub.ContactUs

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAddContact
import com.dev.alahlifc.al_ahlysportsclub.models.mContactUs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsRepository {
    fun contact() : MutableLiveData<Resource<mContactUs>> {
        val result = MutableLiveData<Resource<mContactUs>>()
        result.value = Resource.loading(true)
        Api().contactUs(
           ).enqueue(object : Callback<mContactUs>{
            override fun onResponse(call: Call<mContactUs>, response: Response<mContactUs>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mContactUs>() {}.type
                    val errorResponse: mContactUs = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mContactUs>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }

    fun addContact(
        access_token : String,
        content : String,
        user_name: String,
        user_email: String

    ) : MutableLiveData<Resource<mAddContact>> {
        val result = MutableLiveData<Resource<mAddContact>>()
        result.value = Resource.loading(true)
        Api().addContactMessage(
            access_token,
            content,
            user_name,
            user_email
        ).enqueue(object : Callback<mAddContact>{
            override fun onResponse(call: Call<mAddContact>, response: Response<mAddContact>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mAddContact>() {}.type
                    val errorResponse: mAddContact = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mAddContact>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }

}