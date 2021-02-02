package com.dev.alahlifc.al_ahlysportsclub.LogOut

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mLogout

class LogoutViewModel() : ViewModel() {
    private val logoutRepository =  LogoutRepository()
    var result = MutableLiveData<Resource<mLogout>>()

    fun logout(access_token : String) : MutableLiveData<Resource<mLogout>> {
        result = logoutRepository.logout(access_token)
        return result
    }



}
