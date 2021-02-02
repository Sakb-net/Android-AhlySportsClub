package com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api

class ItemsInCategoriesRepository {

/**
    fun ItemsInCategories(
        lang: String,
        cat_link : String
       ) : MutableLiveData<Resource<mItemsInCategory>> {
        val result = MutableLiveData<Resource<mItemsInCategory>>()
        result.value = Resource.loading(true)
        Api().getItemsInCategory(
            lang,
            cat_link

           ).enqueue(object : Callback<mItemsInCategory>{
            override fun onResponse(call: Call<mItemsInCategory>, response: Response<mItemsInCategory>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mItemsInCategory>() {}.type
                    val errorResponse: mItemsInCategory = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mItemsInCategory>, t: Throwable) {
                Timber.e("err "+t.printStackTrace())
                result.value = Resource.failure(t)
            }
        })

        return result
    }*/


val api = Api()
   var sourceLiveData: LiveData<ItemsInCategoryPagingDataSource>? = null


    fun getProducts(token: String, sort_type : String)
            : ItemsInCategoryResult = getMyListingsFromFactory(VideosPagingDataSourceFactory(token,sort_type,api))

    fun retryGettingPagedShows() = sourceLiveData?.value?.retry()
  //  fun rloadSortGettingPagedShows(sort_type: String) = sourceLiveData?.value?.reloadNewSort(sort_type )

  //  fun refreshGettingPagedShows() = sourceLiveData?.value?.refresh()

    private fun getMyListingsFromFactory(dataSourceFactory: VideosPagingDataSourceFactory): ItemsInCategoryResult{

        sourceLiveData = dataSourceFactory.sourceLiveData




        val networkStatus = Transformations.switchMap(dataSourceFactory.sourceLiveData, { it.networkStatus })
        val initialResult =Transformations.switchMap(dataSourceFactory.sourceLiveData, { it.initialResult })
        val countResult =Transformations.switchMap(dataSourceFactory.sourceLiveData, { it.countProductResult })


        val pagedListConfig = PagedList
            .Config
            .Builder()
            .setEnablePlaceholders(true)
            .setPageSize(TV_SHOWS_PAGE_SIZE)
            .build()

        // LiveData<PagedList<Result>> -> it wrap data ...
        val data = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()

        return ItemsInCategoryResult(data, networkStatus, initialResult,countResult)


    }

//    fun addSortType(sort_type: String) {
//
//        sourceLiveData?.getValue()?.invalidate()
//    }

    companion object {
        private const val TV_SHOWS_PAGE_SIZE = 10
    }




}