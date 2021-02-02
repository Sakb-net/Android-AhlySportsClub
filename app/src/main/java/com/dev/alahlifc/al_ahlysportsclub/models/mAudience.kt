package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mAudience(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {
    data class Data(
        @SerializedName("anwsers")
        val anwsers: MutableList<Anwser?> = mutableListOf(),
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("rate")
        val rate: Int = 0
    )

    data class Anwser(
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("rate")
        val rate: Int = 0
    )
}