package com.dev.alahlifc.al_ahlysportsclub.NewestProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mProduct
import java.io.IOException

class NewestProductsViewModel : ViewModel() {


    val ProductResponse = MutableLiveData<NewestProductResult>()
    // repository to get data from local or private
    val newestProductRepository = NewestProductRepository()
    // doing transformation on the parts of observer .....
    val dataComing : LiveData<PagedList<mProduct.Data>> = Transformations.switchMap(ProductResponse, { it.data })
    val networkStatus : LiveData<NetworkStatus> =  Transformations.switchMap(ProductResponse, { it -> it.networkStatus })
    val initialResult = Transformations.switchMap(ProductResponse, { it.initialResult })
    fun onGetMyListingRequested( token : String) {
        if (ProductResponse.value == null ){ ProductResponse.postValue(newestProductRepository.getVideos(token)) }
        else if (ProductResponse.value != null ) {
            ProductResponse.value!!.initialResult.value.apply {
                when (this) {
                    is  Resource.Failure -> if (e is IOException) {
                        ProductResponse.postValue(
                            newestProductRepository.getVideos(
                                token
                            ))

                    }

                }
            }

        }

    }
    fun onRetryGettingPagedShows() = newestProductRepository.retryGettingPagedShows()



}