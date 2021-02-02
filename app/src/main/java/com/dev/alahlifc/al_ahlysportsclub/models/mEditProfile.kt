package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mEditProfile(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?,
    @SerializedName("user")
    val user: User?
){

data class User(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("state")
    val state: String?
)
}