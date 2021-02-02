package com.dev.alahlifc.al_ahlysportsclub.News

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mNews

data class NewsResult(
    val data: LiveData<PagedList<mNews.Data>>,
    val networkStatus: LiveData<NetworkStatus>,
    val initialResult:LiveData<Resource<List<mNews.Data?>>>
)