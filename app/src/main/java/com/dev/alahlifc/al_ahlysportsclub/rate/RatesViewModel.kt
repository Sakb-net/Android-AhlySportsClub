package com.dev.alahlifc.al_ahlysportsclub.rate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class RatesViewModel() : ViewModel() {
    var page: String = "0"
    private val detailsCommentsRepository =  RatesRepository()
    var result = MutableLiveData<Resource<mComments>>()
    var resultLike = MutableLiveData<Resource<mAddDeleteLike>>()


     fun Comments(
         access_token : String,
         link  : String,
         type : String,
         num_page : String,
         limit : String
    ): MutableLiveData<Resource<mComments>> {

         if (result.value==null)
        result = detailsCommentsRepository.Comments(access_token ,
            link ,
            type ,
            num_page,
            limit )
        return result
    }

    fun reloadComments(
        access_token : String,
        link  : String,
        type : String,
        num_page : String,
        limit : String
    ): MutableLiveData<Resource<mComments>> {

      //  if (result.value==null)
            result = detailsCommentsRepository.Comments(access_token ,
                link ,
                type ,
                num_page,
                limit )
        return result
    }


    fun AddLikeUnLike(
        access_token : String,
        link  : String,
        type : String
    ): MutableLiveData<Resource<mAddDeleteLike>>
            = detailsCommentsRepository.LikeAndUnLike(access_token ,link , type )

    fun DeleteComment(
        access_token : String,
        link  : String,
        type : String
    ): MutableLiveData<Resource<mDeleteComment>>
            = detailsCommentsRepository.DelecteComment(access_token ,link , type )



}
