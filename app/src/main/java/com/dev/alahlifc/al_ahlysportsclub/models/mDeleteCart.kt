package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mDeleteCart(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {

    data class Data(
        @SerializedName("product_cart")
        val productCart: List<Any> = listOf(),
        @SerializedName("total_price_cart")
        val totalPriceCart: Int = 0
    )
}