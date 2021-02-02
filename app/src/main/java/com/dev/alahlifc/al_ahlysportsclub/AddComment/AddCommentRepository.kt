package com.dev.alahlifc.al_ahlysportsclub.AddComment

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCommentRepository {


    fun AddComment(
        access_token : String,
        lang: String,
        content : String,
        link  : String,
        user_email  : String,
        user_name  : String,
        type : String
       ) : MutableLiveData<Resource<mAddComment>> {
        val result = MutableLiveData<Resource<mAddComment>>()
        result.value = Resource.loading(true)
        Api().AddComment(
            access_token ,
            lang,
            content,
            link,
            user_email,
            user_name,
            type
           ).enqueue(object : Callback<mAddComment>{
            override fun onResponse(call: Call<mAddComment>, response: Response<mAddComment>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mAddComment>() {}.type
                    val errorResponse: mAddComment = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.failure(Throwable(errorResponse.message))
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mAddComment>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }


    fun AddReply(
        access_token : String,
        lang: String,
        content : String,
        link  : String,
        link_parent  : String,
        user_email  : String,
        user_name  : String,
        type : String
    ) : MutableLiveData<Resource<mAddComment>> {
        val result = MutableLiveData<Resource<mAddComment>>()
        result.value = Resource.loading(true)
        Api().AddReply(
            access_token ,
            lang,
            content,
            link,
            link_parent,
            user_email,
            user_name,
            type
        ).enqueue(object : Callback<mAddComment>{
            override fun onResponse(call: Call<mAddComment>, response: Response<mAddComment>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mAddComment>() {}.type
                    val errorResponse: mAddComment = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mAddComment>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}