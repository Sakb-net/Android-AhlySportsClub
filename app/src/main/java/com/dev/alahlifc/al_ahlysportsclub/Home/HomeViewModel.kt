package com.dev.alahlifc.al_ahlysportsclub.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mHome

class HomeViewModel : ViewModel() {
    private val homeRepository =  HomeRepository()
    var result = MutableLiveData<Resource<mHome>>()
  var  lastSliderPosition = 0

    fun home(lang : String , limit : String): MutableLiveData<Resource<mHome>> {
        if(result.value == null) {
            result = homeRepository.home(lang, limit)

        }

        return result
    }
}
