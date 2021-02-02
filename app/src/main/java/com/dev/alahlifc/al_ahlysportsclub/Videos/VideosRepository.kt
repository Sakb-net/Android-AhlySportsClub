package com.dev.alahlifc.al_ahlysportsclub.Videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api

class VideosRepository {
  /*  fun getVideos(num_page: String, limit: String, lang : String) : MutableLiveData<Resource<mVideos>> {
        val result = MutableLiveData<Resource<mVideos>>()
        result.value = Resource.loading(true)
        Api().videos(num_page,limit,lang)
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

        return result
    }*/



    val api = Api()
    var sourceLiveData: LiveData<VideosPagingDataSource>? = null


    fun getVideos(token: String): VideosResult = getMyListingsFromFactory(VideosPagingDataSourceFactory(token,api))

    fun retryGettingPagedShows() = sourceLiveData?.value?.retry()

    private fun getMyListingsFromFactory(dataSourceFactory: VideosPagingDataSourceFactory): VideosResult{

        sourceLiveData = dataSourceFactory.sourceLiveData


        val networkStatus = Transformations.switchMap(dataSourceFactory.sourceLiveData, { it.networkStatus })
        val initialResult =Transformations.switchMap(dataSourceFactory.sourceLiveData, { it.initialResult })


        val pagedListConfig = PagedList
            .Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(TV_SHOWS_PAGE_SIZE)
            .build()

        // LiveData<PagedList<Result>> -> it wrap data ...
        val data = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()

        return VideosResult(data, networkStatus, initialResult)


    }

    companion object {
        private const val TV_SHOWS_PAGE_SIZE = 10
    }
}