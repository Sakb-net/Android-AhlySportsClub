package com.dev.alahlifc.al_ahlysportsclub.Videos

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mVideos

data class VideosResult(
    val data: LiveData<PagedList<mVideos.Data>>,
    val networkStatus: LiveData<NetworkStatus>,
    val initialResult:LiveData<Resource<List<mVideos.Data?>>>
)