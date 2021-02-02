package com.dev.alahlifc.al_ahlysportsclub.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class mSubAlbum(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0,
    @SerializedName("sub_albums")
    val subAlbums: MutableList<SubAlbum?> = mutableListOf()
) : Parcelable {

    @Parcelize
    data class Data(
        @SerializedName("created_at")
        val createdAt: String = "",
        @SerializedName("date")
        val date: String = "",
        @SerializedName("image")
        val image: String = "",
        @SerializedName("name")
        val name: String = ""
    ) : Parcelable

    @Parcelize
    data class SubAlbum(
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
    ) : Parcelable
}