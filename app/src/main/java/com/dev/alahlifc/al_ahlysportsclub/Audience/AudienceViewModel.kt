package com.dev.alahlifc.al_ahlysportsclub.Audience

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class AudienceViewModel() : ViewModel() {
    private val audienceRepository =  AudienceRepository()
    var result = MutableLiveData<Resource<mAudience>>()


     fun audience(
        lang: String
    ): MutableLiveData<Resource<mAudience>> {

         if (result.value==null)
        result = audienceRepository.Audience(lang)
        return result
    }



}
