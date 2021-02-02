package com.dev.alahlifc.al_ahlysportsclub.Championships

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class ChampionsViewModel() : ViewModel() {
    private val championsRepository =  ChampionsRepository()
    var result = MutableLiveData<Resource<mChampions>>()


     fun Champions(
        lang: String
    ): MutableLiveData<Resource<mChampions>> {

         if (result.value==null)
        result = championsRepository.Champions(lang)
        return result
    }



}
