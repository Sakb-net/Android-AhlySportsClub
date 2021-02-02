package com.dev.alahlifc.al_ahlysportsclub.Register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mRegisterLogin

class RegisterViewModel() : ViewModel() {
    private val registerRepository =  RegisterRepository()

    fun register(  email: String,
                   display_name: String,
                   password: String,
                   country: String,
                   city: String,
                   state: String,
                   reg_site: String,
                   phone: String,
                   fcm_token: String) : MutableLiveData<Resource<mRegisterLogin>> =
        registerRepository.register(email, display_name, password, country, city, state, reg_site, phone, fcm_token)




}
