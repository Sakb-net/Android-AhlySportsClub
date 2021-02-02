package com.dev.alahlifc.al_ahlysportsclub.FootballTeam.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*

class PlayersViewModel() : ViewModel() {
    var selection: Int =-1
    private val footballTeamRepository =
        PlayersRepository()
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
           result =footballTeamRepository.Players(lang, team_link)
        return result
    }

    fun refresh( lang: String,
                 message: String) : MutableLiveData<Resource<mFootballTeam>> {

        result.value = null

        result =footballTeamRepository.Players(lang, message)
        return result

    }


}
