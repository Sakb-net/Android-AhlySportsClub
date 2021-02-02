package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class mHome(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {

    data class Data(
        @SerializedName("albums")
        val albums: List<Album> = listOf(),
        @SerializedName("count__news_1")
        val countNews1: Int = 0,
        @SerializedName("count__videos_1")
        val countVideos1: Int = 0,
        @SerializedName("link_ticket")
        val linkTicket: String = "",
        @SerializedName("match_next")
        val matchNext: MatchNext ?= MatchNext(),
        @SerializedName("match_perv")
        val matchPerv: MatchPerv = MatchPerv(),
        @SerializedName("news")
        val news: List<New> = listOf(),
        @SerializedName("products")
        val products: List<Product> = listOf(),
        @SerializedName("videos")
        val videos: List<Video> = listOf()
    )

    data class Video(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("extension")
        val extension: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("upload_id")
        val uploadId: String = "",


        @SerializedName("video")
        val video: String = ""
    )

    data class MatchPerv(
        @SerializedName("cart_red1")
        val cartRed1: String = "",
        @SerializedName("cart_red2")
        val cartRed2: String = "",
        @SerializedName("cart_yellow1")
        val cartYellow1: String = "",
        @SerializedName("cart_yellow2")
        val cartYellow2: String = "",
        @SerializedName("data_first")
        val dataFirst: List<Any> = listOf(),
        @SerializedName("data_second")
        val dataSecond: List<Any> = listOf(),
        @SerializedName("date")
        val date: String = "",
        @SerializedName("description")
        val description: String = "",
        @SerializedName("first_goal")
        val firstGoal: Int = 0,
        @SerializedName("first_image")
        val firstImage: String = "",
        @SerializedName("first_team")
        val firstTeam: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("offside1")
        val offside1: String = "",
        @SerializedName("offside2")
        val offside2: String = "",
        @SerializedName("passes1")
        val passes1: String = "",
        @SerializedName("passes2")
        val passes2: String = "",
        @SerializedName("paying_goal1")
        val payingGoal1: String = "",
        @SerializedName("paying_goal2")
        val payingGoal2: String = "",
        @SerializedName("second_goal")
        val secondGoal: Int = 0,
        @SerializedName("second_image")
        val secondImage: String = "",
        @SerializedName("second_team")
        val secondTeam: String = "",
        @SerializedName("strikes1")
        val strikes1: String = "",
        @SerializedName("strikes2")
        val strikes2: String = "",
        @SerializedName("time")
        val time: String = "",
        @SerializedName("type_time")
        val typeTime: String = "",
        @SerializedName("upload")
        val upload: Int = 0,
        @SerializedName("video")
        val video: String = ""
    )

    @Parcelize
    data class New(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("description")
        val description: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("tags")
        val tags:  List<String?>?= listOf(),
        @SerializedName("user_image")
        val userImage: String = "",
        @SerializedName("user_name")
        val userName: String = ""
    ) : Parcelable

    data class MatchNext(
        @SerializedName("cart_red1")
        val cartRed1: String = "",
        @SerializedName("cart_red2")
        val cartRed2: String = "",
        @SerializedName("cart_yellow1")
        val cartYellow1: String = "",
        @SerializedName("cart_yellow2")
        val cartYellow2: String = "",
        @SerializedName("data_first")
        val dataFirst: List<Any> = listOf(),
        @SerializedName("data_second")
        val dataSecond: List<Any> = listOf(),
        @SerializedName("date")
        val date: String = "",
        @SerializedName("description")
        val description: Any = Any(),
        @SerializedName("first_goal")
        val firstGoal: Int = 0,
        @SerializedName("first_image")
        val firstImage: String = "",
        @SerializedName("first_team")
        val firstTeam: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("link_ticket")
        val link_ticket: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("offside1")
        val offside1: String = "",
        @SerializedName("offside2")
        val offside2: String = "",
        @SerializedName("passes1")
        val passes1: String = "",
        @SerializedName("passes2")
        val passes2: String = "",
        @SerializedName("paying_goal1")
        val payingGoal1: String = "",
        @SerializedName("paying_goal2")
        val payingGoal2: String = "",
        @SerializedName("second_goal")
        val secondGoal: Int = 0,
        @SerializedName("second_image")
        val secondImage: String = "",
        @SerializedName("second_team")
        val secondTeam: String = "",
        @SerializedName("strikes1")
        val strikes1: String = "",
        @SerializedName("strikes2")
        val strikes2: String = "",
        @SerializedName("time")
        val time: String = "",
        @SerializedName("type_time")
        val typeTime: String = "",
        @SerializedName("upload")
        val upload: Int = 0,
        @SerializedName("video")
        val video: String = ""
    )

    data class Album(
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = ""
    )

    data class Product(
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
        @SerializedName("description")
        val description: String = "",
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
    )

    data class Color(
        @SerializedName("name")
        val name: String = ""
    )

    data class Fee(
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("price")
        val price: Int = 0,
        @SerializedName("type_price")
        val typePrice: String = ""
    )

    data class Weight(
        @SerializedName("name")
        val name: String = ""
    )

    data class AnotherImage(
        @SerializedName("name")
        val name: String = ""
    )
}