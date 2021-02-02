package com.dev.alahlifc.al_ahlysportsclub.NewestProducts

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mProduct

data class NewestProductResult(
    val data: LiveData<PagedList<mProduct.Data>>,
    val networkStatus: LiveData<NetworkStatus>,
    val initialResult:LiveData<Resource<List<mProduct.Data?>>>
)