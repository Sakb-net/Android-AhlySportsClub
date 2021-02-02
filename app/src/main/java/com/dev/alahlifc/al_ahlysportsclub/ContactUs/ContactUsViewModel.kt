package com.dev.alahlifc.al_ahlysportsclub.ContactUs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAddContact
import com.dev.alahlifc.al_ahlysportsclub.models.mContactUs

class ContactUsViewModel() : ViewModel() {
    private val contactUsRepository =  ContactUsRepository()
    var result = MutableLiveData<Resource<mContactUs>>()

    fun contact() : MutableLiveData<Resource<mContactUs>> {

        if(result.value == null) {
            result = contactUsRepository.contact()
        }

        return result
    }


    fun addContact(
        access_token : String,
        content : String,
        user_name: String,
        user_email: String

    )  : MutableLiveData<Resource<mAddContact>> {
        return contactUsRepository.addContact(
                access_token ,
                content ,
                user_name,
                user_email)
        }

}
