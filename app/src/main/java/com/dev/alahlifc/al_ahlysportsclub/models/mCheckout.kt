package com.dev.alahlifc.al_ahlysportsclub.models
import com.google.gson.annotations.SerializedName


data class mCheckout(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("Message")
    val message: String = "",
    @SerializedName("StatusCode")
    val statusCode: Int = 0
) {
    data class Data(
        @SerializedName("billing_city")
        val billingCity: String = "",
        @SerializedName("billing_country")
        val billingCountry: String = "",
        @SerializedName("billing_postcode")
        val billingPostcode: String = "",
        @SerializedName("billing_state")
        val billingState: String = "",
        @SerializedName("billing_street1")
        val billingStreet1: String = "",
        @SerializedName("checkoutId")
        val checkoutId: String = "",
        @SerializedName("customer_email")
        val customerEmail: String = "",
        @SerializedName("customer_givenName")
        val customerGivenName: String = "",
        @SerializedName("customer_surname")
        val customerSurname: String = "",
        @SerializedName("merchantTransactionId")
        val merchantTransactionId: String = "",
        @SerializedName("ok_chechout")
        val okChechout: Int = 0,
        @SerializedName("shopperResultUrl")
        val shopperResultUrl: String = "",
        @SerializedName("testMode")
        val testMode: String = ""
    )
}