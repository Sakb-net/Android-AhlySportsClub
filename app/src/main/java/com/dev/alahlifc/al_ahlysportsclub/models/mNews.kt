package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class mNews(
    @SerializedName("data")
    val `data`: MutableList<Data?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("StatusCode")
    val statusCode: Int?
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("content")
        val content: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("date")
        val date: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("tags")
        val tags: List<String?>?,
        @SerializedName("user_image")
        val userImage: String?,
        @SerializedName("user_name")
        val userName: String?
    ) : Parcelable
}