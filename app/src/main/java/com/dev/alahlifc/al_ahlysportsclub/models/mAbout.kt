package com.dev.alahlifc.al_ahlysportsclub.models

data class mAbout(
    val Message: String,
    val StatusCode: Int,
    val `data`: Data
) {
    data class Data(
        val all_list: List<String>,
        val content_one: String,
        val content_two: String,
        val image: String,
        val name: String,
        val title_one: String,
        val title_two: String
    )
}