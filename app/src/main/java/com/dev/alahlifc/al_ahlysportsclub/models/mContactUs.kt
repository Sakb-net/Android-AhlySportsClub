package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mContactUs(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
) {

    data class Data(
        @SerializedName("address")
        val address: String?,
        @SerializedName("content")
        val content: Any?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("lat")
        val lat: String?,
        @SerializedName("long")
        val long: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("title")
        val title: String?
    )

}