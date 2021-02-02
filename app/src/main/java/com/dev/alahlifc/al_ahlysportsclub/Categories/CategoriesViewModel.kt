package com.dev.alahlifc.al_ahlysportsclub.Categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mProductCategories

class CategoriesViewModel : ViewModel() {
    private val categoriesRepository =  CategoriesRepository()
    var result = MutableLiveData<Resource<mProductCategories>>()


     fun Categories(
        access_token : String,
        lang: String,
        limit: String,
        num_page: String
    ): MutableLiveData<Resource<mProductCategories>> {

         if (result.value==null)
        result = categoriesRepository.Categories(access_token,lang, limit, num_page)
        return result
    }



}
