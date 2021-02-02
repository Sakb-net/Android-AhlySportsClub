package com.dev.alahlifc.al_ahlysportsclub.Videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mVideos
import java.io.IOException

class VideosViewModel : ViewModel() {

  /*  var pageNumber: Int = 0
    var islOADING : Boolean = false
    private val videosRepository = VideosRepository()
    var result = MutableLiveData<Resource<mVideos>>()


    fun getVideos(num_page: String, limit: String, lang : String) : MutableLiveData<Resource<mVideos>> {
        if(result.value == null) {
            result = videosRepository.getVideos(num_page, limit, lang)
        }
        return result
    }

    fun getMoreVideos(num_page: String, limit: String, lang : String) : MutableLiveData<Resource<mVideos>>
         = videosRepository.getVideos(num_page, limit, lang)*/




    val VideosResponse = MutableLiveData<VideosResult>()
    // repository to get data from local or private
    val videosRepository = VideosRepository()
    // doing transformation on the parts of observer .....
    val dataComing : LiveData<PagedList<mVideos.Data>> = Transformations.switchMap(VideosResponse, { it.data })
    val networkStatus : LiveData<NetworkStatus> =  Transformations.switchMap(VideosResponse, { it -> it.networkStatus })
    val initialResult = Transformations.switchMap(VideosResponse, { it.initialResult })
    fun onGetMyListingRequested( token : String) {
        if (VideosResponse.value == null ){ VideosResponse.postValue(videosRepository.getVideos(token)) }
        else if (VideosResponse.value != null ) {
            VideosResponse.value!!.initialResult.value.apply {
                when (this) {
                    is  Resource.Failure -> if (e is IOException) {
                        VideosResponse.postValue(
                            videosRepository.getVideos(
                                token
                            ))

                    }

                }
            }

        }

    }
    fun onRetryGettingPagedShows() = videosRepository.retryGettingPagedShows()



}