package com.dev.alahlifc.al_ahlysportsclub.AlbumDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class AlbumDetailsViewModel() : ViewModel() {
    private val albumDetailsRepository =  AlbumDetailsRepository()
    var result = MutableLiveData<Resource<mSubAlbum>>()


     fun AlbumDetails(
        lang: String,
        link : String
    ): MutableLiveData<Resource<mSubAlbum>> {

         if (result.value==null)
        result = albumDetailsRepository.SubAlbums(lang,link)
        return result
    }



}
