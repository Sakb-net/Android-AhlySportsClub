package com.dev.alahlifc.al_ahlysportsclub.OthersGames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class OthersGamesViewModel() : ViewModel() {
    private val othersGamesRepository =  OthersGamesRepository()
    var result = MutableLiveData<Resource<mTeams>>()


     fun OtherGames(
        lang: String
    ): MutableLiveData<Resource<mTeams>> {

         if (result.value==null)
        result = othersGamesRepository.OtherGames(lang)
        return result
    }



}
