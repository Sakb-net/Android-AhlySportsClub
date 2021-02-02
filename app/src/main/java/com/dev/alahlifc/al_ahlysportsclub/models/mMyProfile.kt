package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
@Parcelize
data class mMyProfile(
    @SerializedName("Message")
    val message: String,
    @SerializedName("StatusCode")
    val statusCode: Int,
    @SerializedName("user")
    val `data`: Data
) :Parcelable{
    @Parcelize
    data class Data(
        @SerializedName("access_token")
        val accessToken: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("city")
        var city: String,
        @SerializedName("display_name")
        val displayName: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("gender")
        var gender:String?,
        @SerializedName("image")
        var image: String="",
        @SerializedName("phone")
        val phone: String,
        @SerializedName("state")
        val state: String
    ): Parcelable

}*/
@Parcelize
data class mMyProfile(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
): Parcelable
{
    @Parcelize
data class Data(
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
    val gender: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("state")
    val state: String?
) : Parcelable
}