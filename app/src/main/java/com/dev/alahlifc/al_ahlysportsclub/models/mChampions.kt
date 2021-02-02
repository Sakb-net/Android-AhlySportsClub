package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mChampions(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {
    data class Data(
        @SerializedName("data")
        val `data`: MutableList<DataX?> = mutableListOf(),
        @SerializedName("subteams")
        val subteams: List<Any> = listOf(),
        @SerializedName("teams")
        val teams: List<Team> = listOf()
    )

    data class DataX(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("icon_image")
        val iconImage: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = ""
    )

    data class Team(
        @SerializedName("age")
        val age: Any = Any(),
        @SerializedName("birthdate")
        val birthdate: Any = Any(),
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("height")
        val height: Any = Any(),
        @SerializedName("link")
        val link: String = "",
        @SerializedName("location")
        val location: Any = Any(),
        @SerializedName("name")
        val name: String = "",
        @SerializedName("national")
        val national: Any = Any(),
        @SerializedName("num_sport")
        val numSport: Any = Any(),
        @SerializedName("sport")
        val sport: Any = Any(),
        @SerializedName("type")
        val type: String = "",
        @SerializedName("user_image")
        val userImage: String = "",
        @SerializedName("user_type")
        val userType: String = "",
        @SerializedName("weight")
        val weight: Any = Any()
    )
}