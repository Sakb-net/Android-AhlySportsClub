/*
package com.dev.alahlifc.al_ahlysportsclub.Matches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class MatcheViewModel() : ViewModel() {
    private val matchesRepository = MatcheRepository()
    var result = MutableLiveData<Resource<mMatches>>()
    var resultNext = MutableLiveData<Resource<mMatches>>()


    fun PastMatches(
        lang: String,
        limit: String,
        num_page: String,
        type : String
    ): MutableLiveData<Resource<mMatches>> {

        if (result.value == null)
            result = matchesRepository.Matches(lang, limit, num_page, type)
        return result
    }

    fun NexttMatches(
        lang: String,
        limit: String,
        num_page: String,
        type : String
    ): MutableLiveData<Resource<mMatches>> {

        if (resultNext.value==null)
            resultNext = matchesRepository.Matches(lang, limit, num_page,type)
        return resultNext
    }



}
*/
