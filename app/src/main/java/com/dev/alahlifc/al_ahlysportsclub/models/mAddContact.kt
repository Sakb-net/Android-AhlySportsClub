package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mAddContact(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
) {

    data class Data(
        @SerializedName("content")
        val content: String?
    )
}