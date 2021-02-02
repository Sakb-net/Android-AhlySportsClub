package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mAddUpdateCart(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {

    data class Data(
        @SerializedName("product_cart")
        val productCart: List<ProductCart> = listOf(),
        @SerializedName("total_price_cart")
        val totalPriceCart: Double = 0.0
    )

    data class ProductCart(
        @SerializedName("cat_link")
        val catLink: String = "",
        @SerializedName("cat_name")
        val catName: String = "",
        @SerializedName("discount")
        val discount: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("image")
        val image: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("price")
        val price: Double = 0.0,
        @SerializedName("quantity")
        val quantity: Int = 0,
        @SerializedName("total_price")
        val totalPrice: Double = 0.0
    )
}