package com.dev.alahlifc.al_ahlysportsclub.NewestProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api


class NewestProductRepository {



    val api = Api()
    var sourceLiveData: LiveData<NewestProductsPagingDataSource>? = null


    fun getVideos(token: String): NewestProductResult = getMyListingsFromFactory(VideosPagingDataSourceFactory(token,api))

    fun retryGettingPagedShows() = sourceLiveData?.value?.retry()

    private fun getMyListingsFromFactory(dataSourceFactory: VideosPagingDataSourceFactory): NewestProductResult{

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

        return NewestProductResult(data, networkStatus, initialResult)


    }

    companion object {
        private const val TV_SHOWS_PAGE_SIZE = 10
    }
}