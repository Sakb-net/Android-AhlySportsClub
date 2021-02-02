package com.dev.alahlifc.al_ahlysportsclub.Videos

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mNews
import com.dev.alahlifc.al_ahlysportsclub.models.mVideos
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class VideosPagingDataSource (open val  token  :String,open val api: Api)
    : ItemKeyedDataSource<Int, mVideos.Data>() {

    val initialResult = MutableLiveData<Resource<List<mVideos.Data?>>>()
    val networkStatus = MutableLiveData<NetworkStatus>()
    var pageIndex = 0

    var onRetryAction: (() -> Unit)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<mVideos.Data>) {
        initialResult.postValue ( Resource.loading(true))
        loadData(
            onDataLoaded = {
                initialResult.postValue ( Resource.success(it))
                callback.onResult(it.filterNotNull())
            },
            onDataError = {
                initialResult.postValue ( Resource.Failure(it))
            }
            ,
            onServerReturnNull = {
                initialResult.postValue ( Resource.Failure(it))
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<mVideos.Data>) {
        networkStatus.postValue(NetworkStatus.LOADING)
        loadData(
            onDataLoaded = {
                networkStatus.postValue(NetworkStatus.DONE)
                callback.onResult(it/*it.filterNotNull()*/)

            },
            onDataError = {
                onRetryAction = { loadAfter(params, callback) }
                networkStatus.postValue(NetworkStatus.ERROR)
            }
            ,
            onServerReturnNull = {
                networkStatus.postValue(NetworkStatus.DONE)

            }
        )
    }

    fun retry() = onRetryAction?.invoke()

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback< mVideos.Data>) = Unit

    override fun getKey(item:  mVideos.Data): Int = item.link.hashCode()

    protected open fun loadData(onDataLoaded: (results: List< mVideos.Data?>) -> Unit, onDataError: ( t: Throwable) -> Unit,  onServerReturnNull : ( t: Throwable) -> Unit) =


        /**
         *    Api().videos(num_page,limit,lang)
        .enqueue(object : Callback<mVideos>{
        override fun onResponse(call: Call<mVideos>, response: Response<mVideos>) {
        if (response.isSuccessful) {
        result.value = Resource.loading(false)
        result.value = Resource.success(response.body()!!)
        }else{
        result.value = Resource.loading(false)
        val gson = Gson()
        val type = object : TypeToken<mVideos>() {}.type
        val errorResponse: mVideos = gson.fromJson(response.errorBody()!!.charStream(), type)
        result.value = Resource.success(errorResponse)
        //Timber.e(" "+ response.errorBody()!!.string())

        }
        }

        override fun onFailure(call: Call<mVideos>, t: Throwable) {
        result.value = Resource.failure(t)
        }
        })
         */



        api.videos(""+pageIndex ,"10",PrefManager.getLanguage()!!).enqueue(object : Callback<mVideos> {
            override fun onFailure(call: Call<mVideos>, t: Throwable) {
                onDataError(t)
            }

            override fun onResponse(call: Call<mVideos>, response: Response<mVideos>) {
                if (response.isSuccessful) {
                    if( response.body()!!.data!=null ) {
                        pageIndex += 1
                      //  Log.e("now current page is ", "" + pageIndex)
                        response.body()?.let { onDataLoaded(it.data!!) }
                    }else {
                        onServerReturnNull(Throwable("no data"))
                    }

                } else{  // server error
                  /*  var userMessage : String = ""
                    try {
                        val jsonObject: JSONObject? = JSONObject(response.errorBody()?.string())
                        userMessage = jsonObject?.getString("Message").toString()
                    }catch (e :Exception){
                        e.printStackTrace()
                    }*/

                    val gson = Gson()
                    val type = object : TypeToken<mVideos>() {}.type
                    val errorResponse: mNews = gson.fromJson(response.errorBody()!!.charStream(), type)


                    onDataError(Throwable(errorResponse.message))
                }

            }

        })

}

open class VideosPagingDataSourceFactory(open val token : String, open val api: Api) : DataSource.Factory<Int,  mVideos.Data>() {

    var sourceLiveData = MutableLiveData<VideosPagingDataSource>()

    override fun create(): DataSource<Int,  mVideos.Data> {
        val dataSource = VideosPagingDataSource( token , api)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

}