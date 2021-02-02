package com.dev.alahlifc.al_ahlysportsclub.AddRate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class AddRateViewModel() : ViewModel() {
    private val addCommentRepository =  AddRateRepository()
    var result = MutableLiveData<Resource<mAddComment>>()


     fun AddComent(
         access_token : String,
         lang: String,
         content : String,
         link  : String,
         user_email  : String,
         user_name  : String,
         type : String,
         rate : String
    ): MutableLiveData<Resource<mAddComment>> {

       //  if (result.value==null)
        result = addCommentRepository.AddComment(
            access_token ,
            lang,
            content,
            link,
            user_email,
            user_name,
            type,
            rate
        )
        return result
    }


    fun AddReply(
        access_token : String,
        lang: String,
        content : String,
        link  : String,
        link_parent  : String,
        user_email  : String,
        user_name  : String,
        type : String
    ): MutableLiveData<Resource<mAddComment>> {

        //  if (result.value==null)
        result = addCommentRepository.AddReply(
            access_token ,
            lang,
            content,
            link,
            link_parent,
            user_email,
            user_name,
            type
        )
        return result
    }



}
