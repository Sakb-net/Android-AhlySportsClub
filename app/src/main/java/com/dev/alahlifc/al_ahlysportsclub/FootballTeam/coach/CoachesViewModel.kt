package com.dev.alahlifc.al_ahlysportsclub.FootballTeam.coach

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class CoachesViewModel() : ViewModel() {
    private val coachesRepository =  CoachesRepository()
    var result = MutableLiveData<Resource<mFootballTeam>>()


//     fun OtherGames(
//        lang: String
//    ): MutableLiveData<Resource<mTeams>> {
//
//         if (result.value==null)
//        result = footballTeamRepository.OtherGames(lang)
//        return result
//    }



    fun Players(
        lang: String,
        team_link : String
    ): MutableLiveData<Resource<mFootballTeam>> {

        if (result.value==null)
           result =coachesRepository.Players(lang, team_link)
        return result
    }



    fun refresh( lang: String,
                 message: String) : MutableLiveData<Resource<mFootballTeam>> {

        result.value = null

        result =coachesRepository.Players(lang, message)
        return result

    }

}
