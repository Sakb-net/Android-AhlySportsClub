package com.dev.alahlifc.al_ahlysportsclub.Reset

import java.io.IOException
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager

import okhttp3.*
import timber.log.Timber

class SupportInterceptor():  Authenticator {

    /** var ctx : Context ?= null
    var myServiceHder : MyServiceHolder ?= null
    init {
    ctx = context
    myServiceHder = myServiceHolder
    }*/
    /**
     * Authenticator for when the authToken need to be refresh and updated
     * everytime we get a 401 error code
     */
    @Throws(IOException::class)
    override fun authenticate (route: Route?, response: Response?): Request? {
        var requestAvailable: Request? = null

        try {
            Timber.e("error happened in Authentication process")


            // token un valid so request token from server
           // val user : mEditProfile.User? = PrefManager?.getUser()

            val userName_or_email =""+ PrefManager.getUserName_or_email()
            val password = ""+PrefManager.getPassWord()

           // var body = LoginBody(email!!,password!!,phone,PrefManager!!.FirebaseToken!!)

            // var body = LoginBody("mah@gmail.com", "123456", "", "1111")
            var ans = Api().login(userName_or_email, password, "").execute()
            var newAccessToken : String ?= null
            if (ans!!.isSuccessful){
                newAccessToken = ans.body()?.data?.access_token
                Timber.e("------------/////error happened in-------- $newAccessToken")
                val user = ans.body()?.data
                PrefManager.saveUser(user)
            }

            requestAvailable = response?.request()?.newBuilder()
                ?.removeHeader("access-token")
                ?.addHeader("access-token", newAccessToken)
                ?.build()
            return requestAvailable
        } catch (ex: Exception) { }
        return requestAvailable
    }

}