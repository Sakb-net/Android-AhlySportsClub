package com.dev.alahlifc.al_ahlysportsclub.Details

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsCommentsRepository {


    fun Comments(
     access_token : String,
       link  : String,
       type : String,
     num_page : String,
        limit : String
       ) : MutableLiveData<Resource<mComments>> {
        val result = MutableLiveData<Resource<mComments>>()
        result.value = Resource.loading(true)
        Api().Comments(
            access_token ,
            link ,
            type,
            num_page,
            limit
           ).enqueue(object : Callback<mComments>{
            override fun onResponse(call: Call<mComments>, response: Response<mComments>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mComments>() {}.type
                    val errorResponse: mComments = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mComments>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }

    fun LikeAndUnLike(
        access_token : String,
        link  : String,
        type : String
    ) : MutableLiveData<Resource<mAddDeleteLike>> {
        val result = MutableLiveData<Resource<mAddDeleteLike>>()
        result.value = Resource.loading(true)
        Api().LikeOrUnLike(
            access_token ,
            link ,
            type
        ).enqueue(object : Callback<mAddDeleteLike>{
            override fun onResponse(call: Call<mAddDeleteLike>, response: Response<mAddDeleteLike>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mAddDeleteLike>() {}.type
                    val errorResponse: mAddDeleteLike = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mAddDeleteLike>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }


    fun DelecteComment(
        access_token : String,
        link  : String,
        type : String
    ) : MutableLiveData<Resource<mDeleteComment>> {
        val result = MutableLiveData<Resource<mDeleteComment>>()
        result.value = Resource.loading(true)
        Api().DeleteComment(
            access_token ,
            link ,
            type
        ).enqueue(object : Callback<mDeleteComment>{
            override fun onResponse(call: Call<mDeleteComment>, response: Response<mDeleteComment>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mDeleteComment>() {}.type
                    val errorResponse: mDeleteComment = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mDeleteComment>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }




}