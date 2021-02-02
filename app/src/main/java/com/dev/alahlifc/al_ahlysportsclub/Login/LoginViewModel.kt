package com.dev.alahlifc.al_ahlysportsclub.Login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mRegisterLogin

class LoginViewModel() : ViewModel() {
    private val loginRepository =  LoginRepository()

    fun login(  email: String,
                password: String,
                fcm_token: String) : MutableLiveData<Resource<mRegisterLogin>> =
        loginRepository.login(email, password,fcm_token)




}
