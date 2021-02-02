package com.dev.alahlifc.al_ahlysportsclub.News

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mNews
import java.io.IOException

class NewsViewModel() : ViewModel() {
  /*  private val newsRepository = NewsRepository()
    var result = MutableLiveData<Resource<mNews>>()

    fun getNews(num_page: String, limit: String) : MutableLiveData<Resource<mNews>> {

        if(result.value == null) {
            result = newsRepository.getNews(num_page, limit)
        }

        return result
    }*/





    val NewsResponse = MutableLiveData<NewsResult>()
    // repository to get data from local or private
    val newsRepository = NewsRepository()
    // doing transformation on the parts of observer .....
    val dataComing : LiveData<PagedList<mNews.Data>> = Transformations.switchMap(NewsResponse, { it.data })
    val networkStatus : LiveData<NetworkStatus> =  Transformations.switchMap(NewsResponse, { it -> it.networkStatus })
    val initialResult = Transformations.switchMap(NewsResponse, { it.initialResult })
    fun onGetMyListingRequested( token : String) {
        if (NewsResponse.value == null ){ NewsResponse.postValue(newsRepository.getMyListings(token)) }
        else if (NewsResponse.value != null ) {
            NewsResponse.value!!.initialResult.value.apply {
                when (this) {
                    is  Resource.Failure -> if (e is IOException) {
                        NewsResponse.postValue(
                            newsRepository.getMyListings(
                                token
                            ))

                    }

                }
            }

        }

    }
    fun onRetryGettingPagedShows() = newsRepository.retryGettingPagedShows()


}
