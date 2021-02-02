package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mAlbum(
    @SerializedName("data")
    val `data`: MutableList<Data?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
) {

    data class Data(
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("date")
        val date: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("name")
        val name: String?
    )
}