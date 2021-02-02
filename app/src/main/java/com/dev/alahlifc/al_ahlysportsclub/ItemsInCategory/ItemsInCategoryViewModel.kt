package com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import java.io.IOException

class ItemsInCategoryViewModel() : ViewModel() {
   /* private val itemsInCategoriesRepository =  ItemsInCategoriesRepository()
    var result = MutableLiveData<Resource<mItemsInCategory>>()


     fun ItemsInCategories(
         lang: String,
         cat_link : String
    ): MutableLiveData<Resource<mItemsInCategory>> {

         if (result.value==null)
        result = itemsInCategoriesRepository.ItemsInCategories(lang,cat_link)
        return result
    }*/


    val response = MutableLiveData<ItemsInCategoryResult>()
    // repository to get data from local or private
    val itemsInCategoryRepository = ItemsInCategoriesRepository()
    // doing transformation on the parts of observer .....
    val dataComing : LiveData<PagedList<mItemsInCategory.Product?>> = Transformations.switchMap(response, { it.data })
    val networkStatus : LiveData<NetworkStatus> =  Transformations.switchMap(response, { it -> it.networkStatus })
    val initialResult = Transformations.switchMap(response, { it.initialResult })
    val productCountResult = Transformations.switchMap(response, { it.countProductResult })



   // val refreshResult = Transformations.switchMap(response, { it.initialResult })
    fun onGetMyListingRequested(flag :Boolean ,token : String, sort_type : String) {
       if (flag){response.postValue(itemsInCategoryRepository.getProducts(token, sort_type))}

        else if (response.value == null ){ response.postValue(itemsInCategoryRepository.getProducts(token, sort_type)) }
        else if (response.value != null ) {
        //   response.value!!.

            response.value!!.initialResult.value.apply {
                when (this) {
                    is  Resource.Failure -> if (e is IOException) {
                        response.postValue(
                            itemsInCategoryRepository.getProducts(
                                token,sort_type
                            ))

                    }

                }
            }





        }

    }


    fun onRetryGettingPagedShows() = itemsInCategoryRepository.retryGettingPagedShows()

  //  fun onRefreshGettingPagedShows() = itemsInCategoryRepository.refreshGettingPagedShows()

    fun removeSource(){
        //itemsInCategoryRepository.sourceLiveData.
    }

 /*   fun refresh(sort_type : String) {
      // itemsInCategoryRepository.addSortType(sort_type)
itemsInCategoryRepository.rloadSortGettingPagedShows(sort_type)
    }*/


}
