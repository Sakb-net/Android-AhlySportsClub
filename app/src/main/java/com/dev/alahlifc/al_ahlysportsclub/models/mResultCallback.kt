package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mResultCallback(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {
    data class Data(
        @SerializedName("back_color")
        val backColor: String = "",
        @SerializedName("mesage_pay")
        val mesagePay: String = "",
        @SerializedName("ok_pay")
        val okPay: Int = 0,
        @SerializedName("reson_description")
        val resonDescription: String = ""
    )
}