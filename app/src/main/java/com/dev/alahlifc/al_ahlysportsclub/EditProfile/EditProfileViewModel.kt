package com.dev.alahlifc.al_ahlysportsclub.EditProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mEditProfile
import com.dev.alahlifc.al_ahlysportsclub.models.mMyProfile
import com.dev.alahlifc.al_ahlysportsclub.models.mUploadImage

class EditProfileViewModel() : ViewModel() {
    private val editProfileRepository =  EditProfileRepository()
    var result = MutableLiveData<Resource<mEditProfile>>()
    var userInfo : mMyProfile.Data? = null


    fun editProfile(access_token : String,
                    email: String,
                    display_name: String,
                    phone : String,
                    image: String,
                    country: String,
                    city : String,
                    state: String,
                    gender: String) : MutableLiveData<Resource<mEditProfile>> {

        result = editProfileRepository.editProfile(
            access_token, email, display_name,
            phone, image, country,
            city, state, gender)
        return result
    }

    fun uploadImage(access_token : String, image: String) : MutableLiveData<Resource<mUploadImage>> =
        editProfileRepository.uploadImage(access_token,image)

    fun cancelUpload() = editProfileRepository.cancelUploading()

}
