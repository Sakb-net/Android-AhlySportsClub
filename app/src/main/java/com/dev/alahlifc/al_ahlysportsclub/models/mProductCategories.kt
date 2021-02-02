package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class mProductCategories(
    @SerializedName("data")
    val `data`: MutableList<Data?> = mutableListOf<Data?>(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0,
    @SerializedName("count_cart")
    val count_cart: Int = 0


) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("products_count")
        val productsCount: Int = 0,
        @SerializedName("subcategories")
        val subcategories: MutableList<Subcategory?> = mutableListOf()
    ) : Parcelable

    @Parcelize
    data class Subcategory(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("products_count")
        val productsCount: Int = 0
    ) : Parcelable

}