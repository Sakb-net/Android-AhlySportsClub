package com.dev.alahlifc.al_ahlysportsclub.Album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAlbum
import java.io.IOException

class AlbumsViewModel : ViewModel() {
//    private val albumsRepository = AlbumsRepository()
//    var result = MutableLiveData<Resource<mAlbum>>()
//
//    fun getAlbums(num_page: String, limit: String, lang : String) : MutableLiveData<Resource<mAlbum>> {
//
//        if(result.value == null) {
//            result = albumsRepository.getAlbums(num_page, limit, lang)
//        }
//
//        return result
//    }





    val albumsResponse = MutableLiveData<AlbumResult>()
    // repository to get data from local or private
    val newsRepository = AlbumRepository()
    // doing transformation on the parts of observer .....
    val dataComing : LiveData<PagedList<mAlbum.Data>> = Transformations.switchMap(albumsResponse, { it.data })
    val networkStatus : LiveData<NetworkStatus> =  Transformations.switchMap(albumsResponse, { it -> it.networkStatus })
    val initialResult = Transformations.switchMap(albumsResponse, { it.initialResult })
    fun onGetMyListingRequested( token : String) {
        if (albumsResponse.value == null ){ albumsResponse.postValue(newsRepository.getMyListings(token)) }
        else if (albumsResponse.value != null ) {
            albumsResponse.value!!.initialResult.value.apply {
                when (this) {
                    is  Resource.Failure -> if (e is IOException) {
                        albumsResponse.postValue(
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