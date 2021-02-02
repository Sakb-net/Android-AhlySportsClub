package com.dev.alahlifc.al_ahlysportsclub.Album

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAlbum

data class AlbumResult(
    val data: LiveData<PagedList<mAlbum.Data>>,
    val networkStatus: LiveData<NetworkStatus>,
    val initialResult:LiveData<Resource<List<mAlbum.Data?>>>
)