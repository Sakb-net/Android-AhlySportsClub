package com.dev.alahlifc.al_ahlysportsclub.Register

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mRegisterLogin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {
    fun register(email: String,
                 display_name: String,
                 password: String,
                 country: String,
                 city: String,
                 state: String,
                 reg_site: String,
                 phone: String,
                 fcm_token: String) : MutableLiveData<Resource<mRegisterLogin>> {
        val result = MutableLiveData<Resource<mRegisterLogin>>()
        result.value = Resource.loading(true)
        Api().register(
            email,
            display_name,
            password,
            country,
            city,
            state,
            reg_site,
            phone,
            fcm_token).enqueue(object : Callback<mRegisterLogin>{
            override fun onResponse(call: Call<mRegisterLogin>, response: Response<mRegisterLogin>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mRegisterLogin>() {}.type
                    val errorResponse: mRegisterLogin = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mRegisterLogin>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }

}