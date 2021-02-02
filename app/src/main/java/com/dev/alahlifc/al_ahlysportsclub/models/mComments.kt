package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class mComments(
    @SerializedName("data")
    val `data`: MutableList<Data?> = mutableListOf(),
    @SerializedName("count_data")
    val countData: String = "",
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {

    @Parcelize
    data class Data(
//        @SerializedName("audio")
//        val audio: Any = Any(),
        @SerializedName("child_comments")
        val childComments: MutableList<ChildComment?> ?= mutableListOf(),
        @SerializedName("content")
        var content: String = "",
        @SerializedName("created_at")
        val createdAt: CreatedAt = CreatedAt(),
        @SerializedName("date")
        val date: String = "",
//        @SerializedName("image")
//        val image: Any = Any(),
        @SerializedName("like")
        var like: Boolean = false,
        @SerializedName("link")
        val link: String = "",
        @SerializedName("num_like")
        var numLike: String = "",
        @SerializedName("owner_data")
        val ownerData: String = "",
        @SerializedName("parent_id")
        val parentId: Int ?= 0,
        @SerializedName("parent_user_image")
        var parentUserImage: String = "",
        @SerializedName("parent_user_name")
        var parentUserName: String = "",
        @SerializedName("star_rate")
        val starRate: Int = 0
        //,
//        @SerializedName("video")
//        val video: Any = Any()
    ) : Parcelable


    @Parcelize
    data class ChildComment(
//        @SerializedName("audio")
//        val audio: Any = Any(),
        @SerializedName("content")
        val content: String = "",
        @SerializedName("created_at")
        val createdAt: CreatedAt = CreatedAt(),
        @SerializedName("date")
        val date: String = "",
//        @SerializedName("image")
//        val image: Any = Any(),
        @SerializedName("like")
        var like: Boolean = false,
        @SerializedName("link")
        val link: String = "",
        @SerializedName("num_like")
        var numLike: String = "",
        @SerializedName("owner_data")
        val ownerData: String = "",
        @SerializedName("parent_id")
        val parentId: Int = 0,
        @SerializedName("parent_user_image")
        val parentUserImage: String = "",
        @SerializedName("parent_user_name")
        val parentUserName: String = "",
        @SerializedName("replay_id")
        val replayId: Int = 0,
//        @SerializedName("replay_user")
//        val replayUser:@RawValue Any = Any(),
        @SerializedName("replay_user_image")
        val replayUserImage: String = "",
        @SerializedName("replay_user_name")
        val replayUserName: String = "",
        @SerializedName("star_rate")
        val starRate: Int = 0
//        ,
//        @SerializedName("video")
//        val video:@RawValue Any  = Any()
    ) : Parcelable

    @Parcelize
    data class CreatedAt(
        @SerializedName("date")
        val date: String = "",
        @SerializedName("timezone")
        val timezone: String = "",
        @SerializedName("timezone_type")
        val timezoneType: Int = 0
    ) : Parcelable
}