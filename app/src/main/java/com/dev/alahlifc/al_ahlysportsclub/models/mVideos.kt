package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName

data class mVideos(
    @SerializedName("data")
    val `data`: MutableList<Data?>? = mutableListOf(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {

    data class Data(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("extension")
        val extension: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("upload")
        val upload: Int = 0,
        @SerializedName("upload_id")
        val uploadId: String = "",
        @SerializedName("video")
        val video: String = ""
    )
}