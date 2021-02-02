package com.dev.alahlifc.al_ahlysportsclub.FootballTeam.player

import androidx.lifecycle.MutableLiveData
import com.dev.alahlifc.al_ahlysportsclub.Reset.Api
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayersRepository {


    fun OtherGames(
      lang: String
       ) : MutableLiveData<Resource<mTeams>> {
        val result = MutableLiveData<Resource<mTeams>>()
        result.value = Resource.loading(true)
        Api().Teams(
            lang
           ).enqueue(object : Callback<mTeams>{
            override fun onResponse(call: Call<mTeams>, response: Response<mTeams>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mTeams>() {}.type
                    val errorResponse: mTeams = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mTeams>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }


    fun Players(
        lang: String,
        team_link : String
    ) : MutableLiveData<Resource<mFootballTeam>> {
        val result = MutableLiveData<Resource<mFootballTeam>>()
        result.value = Resource.loading(true)
        Api().FootballTeam(
            lang,
            team_link
        ).enqueue(object : Callback<mFootballTeam>{
            override fun onResponse(call: Call<mFootballTeam>, response: Response<mFootballTeam>) {
                if (response.isSuccessful) {
                    result.value = Resource.loading(false)
                    result.value = Resource.success(response.body()!!)
                }else{
                    result.value = Resource.loading(false)
                    val gson = Gson()
                    val type = object : TypeToken<mFootballTeam>() {}.type
                    val errorResponse: mFootballTeam = gson.fromJson(response.errorBody()!!.charStream(), type)
                    result.value = Resource.success(errorResponse)
                    //Timber.e(" "+ response.errorBody()!!.string())

                }
            }

            override fun onFailure(call: Call<mFootballTeam>, t: Throwable) {
                result.value = Resource.failure(t)
            }
        })

        return result
    }







}