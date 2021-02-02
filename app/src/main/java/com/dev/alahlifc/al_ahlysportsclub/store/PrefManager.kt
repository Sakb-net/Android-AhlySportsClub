package com.dev.alahlifc.al_ahlysportsclub.store

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.dev.alahlifc.al_ahlysportsclub.models.mRegisterLogin
import com.google.gson.Gson

object PrefManager {
    private const val SRF_SHARED_NAME = "SRF_SHARED_NAME"
    private const val SRF_KEY_USER = "SRF_KEY_USER"
    private const val SRF_KEY_Pass = "SRF_KEY_Pass"
    private const val SRF_KEY_userName_or_email = "SRF_KEY_userName_or_email"

    private const val SRF_KEY_REMEMBER_FLAG = "SRF_KEY_REMEMBER_FLAG"
    private const val SRF_KEY_langs_arr = "SRF_KEY_langs_arr"


    private const val SRF_KEY_CategoryAndSub = "SRF_KEY_CategoryAndSub"
    private const val FTOKEN = "Token"
    private const val Language = "lang"
    private const val cart_cont = "cart_cont"
    private const val countryId = "countryIdcountryId"

    private const val flagIsServiceRunnig = "flagIsServiceRunnig"
    private const val srf_password = "srf_password"

    private const val srf_fcm_chat_item = "srf_fcm_chat_item"


    var sharedPreferences: SharedPreferences? = null
        private set
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(SRF_SHARED_NAME, MODE_PRIVATE)
    }


    fun saveLanguage(string: String) = sharedPreferences?.edit()?.putString(Language, string)?.apply()

    fun getLanguage(): String? = sharedPreferences?.getString(Language, "")


    fun saveCartCount(count: Int) = sharedPreferences?.edit()?.putInt(cart_cont, count)?.apply()
    fun getCartCount(): Int? = sharedPreferences?.getInt(cart_cont, 0)


    fun saveUser(user: mRegisterLogin.Data?) =
        sharedPreferences?.edit()?.putString(SRF_KEY_USER, Gson().toJson(user))?.apply()

    fun saveRememberMeFlag(flag : Boolean) =   sharedPreferences?.edit()?.putBoolean(SRF_KEY_REMEMBER_FLAG,flag)?.apply()
    fun getRememberMeFlag(): Boolean? =  sharedPreferences?.getBoolean(SRF_KEY_REMEMBER_FLAG,true)

    fun getUser(): mRegisterLogin.Data? = Gson().fromJson(
        sharedPreferences?.getString(SRF_KEY_USER, ""),
        mRegisterLogin.Data::class.java
    )

    fun savePassword(password: String) =  sharedPreferences?.edit()?.putString(SRF_KEY_Pass,password)?.apply()
    fun getPassWord() : String ?=   sharedPreferences?.getString(SRF_KEY_Pass,"")

    fun saveUserName_or_email(userName_or_email: String) =  sharedPreferences?.edit()?.putString(SRF_KEY_userName_or_email,userName_or_email)?.apply()
    fun getUserName_or_email() : String ?=   sharedPreferences?.getString(SRF_KEY_userName_or_email,"")

    /**





    fun savePassword(string: String) =
        sharedPreferences?.edit()?.putString(srf_password, string)?.apply()

    fun getPassword(): String?=  sharedPreferences?.getString(srf_password, "")



    fun saveCategoryAndSub(homeCategory : HomeCategory?) =
        sharedPreferences?.edit()?.putString(SRF_KEY_CategoryAndSub, Gson().toJson(homeCategory))?.apply()

    fun getCategoryAndSub(): HomeCategory? = Gson().fromJson(
        sharedPreferences?.getString(SRF_KEY_CategoryAndSub, ""),
        HomeCategory::class.java
    )

    fun setIsServiceRunnig(flag : Boolean) =
        sharedPreferences?.edit()?.putBoolean(flagIsServiceRunnig, flag)?.apply()

    fun getIsServiceRunnig(): Boolean? =
        sharedPreferences?.getBoolean(flagIsServiceRunnig, false)






    fun saveCountryID(id: Int) =
        sharedPreferences?.edit()?.putInt(countryId, id)?.apply()

    fun getCountryID(): Int? = sharedPreferences?.getInt(countryId, -1)


    val TOKEN: String get() = getUser()?.data?.accessToken?.token ?: ""

    fun saveFirebaseToken(token: String) =
        sharedPreferences?.edit()?.putString(FTOKEN, token)?.apply()

    val FirebaseToken: String?
        get() =  sharedPreferences?.getString(FTOKEN, "")


    fun saveNotificationType(type : Int) =
        sharedPreferences?.edit()?.putInt("NOTIFICATION_TYPE___", type)?.apply()

    val NotificationsTYPE: Int
        get() = sharedPreferences!!.getInt("NOTIFICATION_TYPE___", -1)

    fun saveFcmChatItem(item  : FcmChatItem) =
        sharedPreferences?.edit()?.putString(srf_fcm_chat_item, Gson().toJson(item))?.apply()

    fun getFCM_ChatItem(): FcmChatItem =
        Gson().fromJson(
            sharedPreferences?.getString(srf_fcm_chat_item, ""),
            FcmChatItem::class.java
        )*/



}