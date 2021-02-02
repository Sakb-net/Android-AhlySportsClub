package com.dev.alahlifc.al_ahlysportsclub.AboutClub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mAbout

class AboutClubViewModel() : ViewModel() {
    private val aboutClubRepository =  AboutClubRepository()
    var result = MutableLiveData<Resource<mAbout>>()

    fun about() : MutableLiveData<Resource<mAbout>> {

        if(result.value == null) {
            result = aboutClubRepository.about()
        }

        return result
    }
}
