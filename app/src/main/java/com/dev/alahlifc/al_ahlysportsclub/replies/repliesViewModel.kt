package com.dev.alahlifc.al_ahlysportsclub.replies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class repliesViewModel() : ViewModel() {
    private val detailsCommentsRepository =  RepliesRepository()
    var result = MutableLiveData<Resource<mComments>>()
    var resultLike = MutableLiveData<Resource<mAddDeleteLike>>()




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
