package com.dev.alahlifc.al_ahlysportsclub.Calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class CalendarViewModel() : ViewModel() {
    private val calendarRepository =  CalendarRepository()
    var result = MutableLiveData<Resource<mCalendar>>()


     fun Calendar(
        lang: String
    ): MutableLiveData<Resource<mCalendar>> {

         if (result.value==null)
        result = calendarRepository.Calendar(lang)
        return result
    }



}
