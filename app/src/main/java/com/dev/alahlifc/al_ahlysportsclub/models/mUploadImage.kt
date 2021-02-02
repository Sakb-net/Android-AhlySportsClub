package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mUploadImage(
    @SerializedName("data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
)