package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
data class mAddComment(
    @SerializedName("data")
    val `data`: Int = 0,
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0,
    @SerializedName("subscribed")
    val subscribed: Boolean = false
)*/


/**
@Parcelize
data class mAddComment(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("state_add")
    val stateAdd: Int = 0,
    @SerializedName("StatusCode")
    val statusCode: Int = 0) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("audio")
        val audio: @RawValue Any? = Any(),
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("email")
        val email: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("image")
        val image:@RawValue  Any ?= Any(),
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("parent_one_id")
        val parentOneId: Int = 0,
        @SerializedName("parent_two_id")
        val parentTwoId:@RawValue  Any? = Any(),
        @SerializedName("type")
        val type: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("user_id")
        val userId:@RawValue  Any ?= Any(),
        @SerializedName("user_image")
        val userImage: String = "",
        @SerializedName("video")
        val  video:@RawValue Any ?= Any(),
        @SerializedName("video_id")
        val videoId: Int = 0


    , @SerializedName("product_id")
        val productId: Int = 0,
        @SerializedName("rate")
        val rate: String = "0"
    ) : Parcelable
}*/

@Parcelize
data class mAddComment(
    @SerializedName("data")
    val `data`: @RawValue Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("state_add")
    val stateAdd: Int = 0,
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("email")
        val email: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("link")
        val link: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("parent_one_id")
        val parentOneId: Int ?= 0,
        @SerializedName("parent_two_id")
        val parentTwoId:@RawValue Int ?= 0,
        @SerializedName("product_id")
        val productId: Int = 0,
        @SerializedName("rate")
        val rate: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("updated_at")
        val updatedAt: String = "",
        @SerializedName("user_id")
        val userId:Int? = 0,
        @SerializedName("user_image")
        val userImage: String = ""
    ) : Parcelable
}


