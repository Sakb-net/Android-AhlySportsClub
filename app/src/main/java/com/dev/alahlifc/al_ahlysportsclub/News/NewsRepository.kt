package com.dev.alahlifc.al_ahlysportsclub.News

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api

class NewsRepository {
    /*fun getNews(num_page: String, limit: String) : MutableLiveData<Resource<mNews>> {
        val result = MutableLiveData<Resource<mNews>>()
        result.value = Resource.loading(true)
        Api().news(num_page,limit)
            .enqueue(object : Callback<mNews>{
            override fun onResponse(call: Call<mNews>, response: Response<mNews>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mNews>() {}.type
                    val errorResponse: mNews = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mNews>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }*/



    val api = Api()
    var sourceLiveData: LiveData<NewsPagingDataSource>? = null


    fun getMyListings(token: String): NewsResult = getMyListingsFromFactory(NewsPagingDataSourceFactory(token,api))

    fun retryGettingPagedShows() = sourceLiveData?.value?.retry()

    private fun getMyListingsFromFactory(dataSourceFactory: NewsPagingDataSourceFactory): NewsResult{

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

        return NewsResult(data, networkStatus, initialResult)


    }

    companion object {
        private const val TV_SHOWS_PAGE_SIZE = 10
    }


}