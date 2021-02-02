package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class mProduct(
    @SerializedName("data")
    val `data`: List<Data> = listOf(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) : Parcelable
{
    @Parcelize
data class Data(
    @SerializedName("another_image")
    val anotherImage: List<AnotherImage> = listOf(),
    @SerializedName("cat_link")
    val catLink: String = "",
    @SerializedName("cat_name")
    val catName: String = "",
    @SerializedName("city_made")
    val cityMade: String = "",
    @SerializedName("color")
    val color: List<Color> = listOf(),
    @SerializedName("dec_prod")
    val decProd: List<DecProd> = listOf(),
    @SerializedName("description")
    val description: String ?= "",
    @SerializedName("discount")
    val discount: Int = 0,
    @SerializedName("fees")
    val fees: List<Fee> = listOf(),
    @SerializedName("image")
    val image: String = "",
    @SerializedName("link")
    val link: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("number_prod")
    val numberProd: Int = 0,
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("rate")
    val rate: Int = 0,
    @SerializedName("sale_number_prod")
    val saleNumberProd: Int = 0,
    @SerializedName("star_rate")
    val starRate: Int = 0,
    @SerializedName("total_price")
    val totalPrice: Double = 0.0,
    @SerializedName("valid_number_prod")
    val validNumberProd: Int = 0,
    @SerializedName("weight")
    val weight: List<Weight> = listOf()
) : Parcelable

    @Parcelize
data class Fee(
    @SerializedName("link")
    val link: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id : Int =-1 ,
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("total_price")
    val totalPrice: Int = 0,
    @SerializedName("type_price")
    val typePrice: String = "",

    var addedName: String = "",
    var isSelected : Boolean = false
) : Parcelable

    @Parcelize
data class Weight(
    @SerializedName("name")
    val name: String = ""
) : Parcelable

    @Parcelize
data class Color(
    @SerializedName("name")
    val name: String = ""
) : Parcelable

    @Parcelize
data class AnotherImage(
    @SerializedName("name")
    val name: String = ""
) : Parcelable

    @Parcelize
data class DecProd(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("discount")
    val discount: Int = 0,
    @SerializedName("price")
    val price: String = "",
    @SerializedName("weight")
    val weight: String = ""
) : Parcelable
}