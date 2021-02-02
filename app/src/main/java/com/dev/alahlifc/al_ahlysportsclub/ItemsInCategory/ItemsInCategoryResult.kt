package com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mItemsInCategory

data class ItemsInCategoryResult(
    val data: LiveData<PagedList<mItemsInCategory.Product?>>,
    val networkStatus: LiveData<NetworkStatus>,
    val initialResult:LiveData<Resource<List<mItemsInCategory.Product?>>>,
    val countProductResult : LiveData<Int>

)