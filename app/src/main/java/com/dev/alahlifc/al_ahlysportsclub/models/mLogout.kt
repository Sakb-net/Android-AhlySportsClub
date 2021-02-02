package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mLogout(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
) {

    data class Data(
        @SerializedName("access_token")
        val accessToken: String?
    )
}