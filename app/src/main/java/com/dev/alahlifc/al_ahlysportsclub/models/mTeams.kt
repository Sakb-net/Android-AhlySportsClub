package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class mTeams(
    @SerializedName("data")
    val `data`: MutableList<Data> = mutableListOf(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("count_subteam")
        val countSubteam: Int = 0,
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("subteams")
        val subteams: List<Subteam> = listOf(),
        @SerializedName("type")
        val type: String = ""
    ) : Parcelable

    @Parcelize
    data class Subteam(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("count_subteam")
        val countSubteam: Int = 0,
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("subteams")
        val subteams: @RawValue List<Any> = listOf(),
        @SerializedName("type")
        val type: String = ""
    ) : Parcelable
}