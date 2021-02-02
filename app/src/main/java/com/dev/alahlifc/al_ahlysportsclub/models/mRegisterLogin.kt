package com.dev.alahlifc.al_ahlysportsclub.models

data class mRegisterLogin(
    val Message: String,
    val StatusCode: Int,
    val `data`: Data?
) {
    data class Data(
        var access_token: String,
        val address: String,
        val city: String,
        val display_name: String,
        val email: String,
        val gender: Any,
        val image: String,
        val new_fcm_token: Any,
        val phone: String,
        val state: String
    )
}