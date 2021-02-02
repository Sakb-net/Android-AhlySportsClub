package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class mFootballTeam(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {
    data class Data(
        @SerializedName("coaches")
        val coaches: MutableList<Coache> = mutableListOf(),
        @SerializedName("content")
        val content: String ? = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("players")
        val players: MutableList<Player> = mutableListOf()
    )

    @Parcelize
    data class Player(
        @SerializedName("age")
        val age: String = "",
        @SerializedName("birthdate")
        val birthdate: String = "",
        @SerializedName("content")
        val content: String ?= "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("height")
        val height: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("location")
        val location: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("national")
        val national: String = "",
        @SerializedName("num_sport")
        val numSport: String = "",
        @SerializedName("sport")
        val sport: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("user_image")
        val userImage: String = "",
        @SerializedName("user_type")
        val userType: String = "",
        @SerializedName("weight")
        val weight: String = ""
    ) : Parcelable

    @Parcelize
    data class Coache(
        @SerializedName("age")
        val age: String = "",
        @SerializedName("birthdate")
        val birthdate: String = "",
        @SerializedName("content")
        val content: String ?="",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("height")
        val height: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("location")
        val location: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("national")
        val national: String = "",
        @SerializedName("num_sport")
        val numSport: String ?= "",
        @SerializedName("sport")
        val sport: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("user_image")
        val userImage: String = "",
        @SerializedName("user_type")
        val userType: String = "",
        @SerializedName("weight")
        val weight: String = ""
    ) : Parcelable
}