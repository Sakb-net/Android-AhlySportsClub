package com.dev.alahlifc.al_ahlysportsclub.Album

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api

class AlbumRepository {




    val api = Api()
    var sourceLiveData: LiveData<AlbumPagingDataSource>? = null


    fun getMyListings(token: String): AlbumResult = getMyListingsFromFactory(AlbumPagingDataSourceFactory(token,api))

    fun retryGettingPagedShows() = sourceLiveData?.value?.retry()

    private fun getMyListingsFromFactory(dataSourceFactory: AlbumPagingDataSourceFactory): AlbumResult{

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

        return AlbumResult(data, networkStatus, initialResult)


    }

    companion object {
        private const val TV_SHOWS_PAGE_SIZE = 10
    }


}