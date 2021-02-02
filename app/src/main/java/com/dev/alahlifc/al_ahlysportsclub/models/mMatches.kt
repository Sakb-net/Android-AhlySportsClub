package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class mMatches(
    @SerializedName("data")
    val `data`: MutableList<Data> = mutableListOf(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) : Parcelable {

    @Parcelize
    data class Data(
        @SerializedName("cart_red1")
        val cartRed1: String = "",
        @SerializedName("cart_red2")
        val cartRed2: String = "",
        @SerializedName("cart_yellow1")
        val cartYellow1: String = "",
        @SerializedName("cart_yellow2")
        val cartYellow2: String = "",
        @SerializedName("data_first")
        val dataFirst: @RawValue List<Any> ? = listOf(),
        @SerializedName("data_second")
        val dataSecond: @RawValue List<Any> ?= listOf(),
        @SerializedName("date")
        val date: String = "",
        @SerializedName("description")
        val description: String ?= "",
        @SerializedName("first_goal")
        val firstGoal: Int = 0,
        @SerializedName("first_image")
        val firstImage: String = "",
        @SerializedName("first_team")
        val firstTeam: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("offside1")
        val offside1: String = "",
        @SerializedName("offside2")
        val offside2: String = "",
        @SerializedName("passes1")
        val passes1: String = "",
        @SerializedName("passes2")
        val passes2: String = "",
        @SerializedName("paying_goal1")
        val payingGoal1: String = "",
        @SerializedName("paying_goal2")
        val payingGoal2: String = "",
        @SerializedName("second_goal")
        val secondGoal: Int = 0,
        @SerializedName("second_image")
        val secondImage: String = "",
        @SerializedName("second_team")
        val secondTeam: String = "",
        @SerializedName("strikes1")
        val strikes1: String = "",
        @SerializedName("strikes2")
        val strikes2: String = "",
        @SerializedName("time")
        val time: String = "",
        @SerializedName("type_time")
        val typeTime: String = "",
        @SerializedName("upload")
        val upload: Int = 0,
        @SerializedName("video")
        val video: String = ""
    ) : Parcelable
}