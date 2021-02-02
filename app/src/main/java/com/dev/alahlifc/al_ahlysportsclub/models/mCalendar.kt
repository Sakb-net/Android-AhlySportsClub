package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mCalendar(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {
    data class Data(
        @SerializedName("data")
        val `data`: MutableList<DataX?> = mutableListOf()
    )

    data class DataX(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("image")
        val image: Any = Any(),
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = ""
    )
}