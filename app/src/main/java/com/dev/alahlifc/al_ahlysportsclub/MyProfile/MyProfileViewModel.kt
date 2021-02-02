package com.dev.alahlifc.al_ahlysportsclub.MyProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mMyProfile

class MyProfileViewModel() : ViewModel() {
    private val myProfileRepository =  MyProfileRepository()
    var result = MutableLiveData<Resource<mMyProfile>>()

    fun fillProfile(access_token : String) : MutableLiveData<Resource<mMyProfile>> {

        if(result.value == null) {
            result = myProfileRepository.fillMyProfile(access_token)
        }

        return result
    }
}
