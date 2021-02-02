package com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import com.dev.alahlifc.al_ahlysportsclub.Base.NetworkStatus
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mItemsInCategory
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

open class ItemsInCategoryPagingDataSource (open val  linkk:String,open val sort_type:String,open val api: Api)
    : ItemKeyedDataSource<Int,mItemsInCategory.Product>() {

    val initialResult = MutableLiveData<Resource<List<mItemsInCategory.Product?>>>()
    val countProductResult = MutableLiveData<Int>()
    val networkStatus = MutableLiveData<NetworkStatus>()


    var pageIndex = 0

    var onRetryAction: (() -> Unit)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<mItemsInCategory.Product?>) {
        initialResult.postValue ( Resource.loading(true))
        loadData(
            onDataLoaded = {countProduct, it ->
                countProductResult.postValue ( countProduct)
                initialResult.postValue ( Resource.success(it))
                callback.onResult(it.filterNotNull())
            },
            onDataError = {
                initialResult.postValue ( Resource.Failure(it))
            }
            ,
            onServerReturnNull = {
                initialResult.postValue ( Resource.Failure(it))
            }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<mItemsInCategory.Product>) {
        networkStatus.postValue(NetworkStatus.LOADING)
        loadData(
            onDataLoaded = { countProduct, it ->
                networkStatus.postValue(NetworkStatus.DONE)
                callback.onResult(it/*it.filterNotNull()*/)

            },
            onDataError = {
                onRetryAction = { loadAfter(params, callback) }
                networkStatus.postValue(NetworkStatus.ERROR)
            }
            ,
            onServerReturnNull = {
                networkStatus.postValue(NetworkStatus.DONE)

            }
        )
    }

    fun retry() = onRetryAction?.invoke()



    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<mItemsInCategory.Product>) = Unit

    override fun getKey(item: mItemsInCategory.Product): Int = item.link.hashCode()

    protected open fun loadData(onDataLoaded: (countProducts : Int ,results: List<mItemsInCategory.Product?>) -> Unit, onDataError: ( t: Throwable) -> Unit,  onServerReturnNull : ( t: Throwable) -> Unit) =
        /**
         *    Api().videos(num_page,limit,lang)
        .enqueue(object : Callback<mVideos>{
        override fun onResponse(call: Call<mVideos>, response: Response<mVideos>) {
        if (response.isSuccessful) {
        result.value = Resource.loading(false)
        result.value = Resource.success(response.body()!!)
        }else{
        result.value = Resource.loading(false)
        val gson = Gson()
        val type = object : TypeToken<mVideos>() {}.type
        val errorResponse: mVideos = gson.fromJson(response.errorBody()!!.charStream(), type)
        result.value = Resource.success(errorResponse)
        //Timber.e(" "+ response.errorBody()!!.string())

        }
        }

        override fun onFailure(call: Call<mVideos>, t: Throwable) {
        result.value = Resource.failure(t)
        }
        })
         */
        api.getItemsInCategory(PrefManager.getLanguage()!!,""+linkk,""+pageIndex,sort_type).enqueue(object : Callback<mItemsInCategory> {
            override fun onFailure(call: Call<mItemsInCategory>, t: Throwable) {
                onDataError(t)
            }

            override fun onResponse(call: Call<mItemsInCategory>, response: Response<mItemsInCategory>) {
                if (response.isSuccessful) {
                    if( response.body()!!.data!=null ) {
                        pageIndex += 1
                      //  Log.e("now current page is ", "" + pageIndex)
                        response.body()?.let { onDataLoaded(it.count_products,it.data.products) }

                    }else {
                        onServerReturnNull(Throwable("no data"))
                    }

                } else{  // server error
                  /*  var userMessage : String = ""
                    try {
                        val jsonObject: JSONObject? = JSONObject(response.errorBody()?.string())
                        userMessage = jsonObject?.getString("Message").toString()
                    }catch (e :Exception){
                        e.printStackTrace()
                    }*/

                    val gson = Gson()
                    val type = object : TypeToken<mItemsInCategory>() {}.type
                    //val errorResponse: mItemsInCategory = gson.fromJson(response.errorBody()!!.charStream(), type)


                  //  Timber.e(""+errorResponse.data)
                    //Timber.e(""+errorResponse.toString())
                    //Timber.e(""+errorResponse.mesage)
                    onDataError(Throwable("Server Error"))
                }

            }

        })

}

open class VideosPagingDataSourceFactory(open val token : String,open val sort_type: String,open val api: Api) : DataSource.Factory<Int,mItemsInCategory.Product>() {

    var sourceLiveData = MutableLiveData<ItemsInCategoryPagingDataSource>()


    override fun create(): DataSource<Int, mItemsInCategory.Product> {
        val dataSource = ItemsInCategoryPagingDataSource( token ,sort_type, api)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

}