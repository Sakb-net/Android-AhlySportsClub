package com.dev.alahlifc.al_ahlysportsclub.Reset


import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

import java.io.IOException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.lang.reflect.AccessibleObject.setAccessible
import java.net.HttpURLConnection


class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var mainResponse = chain.proceed(chain.request())
        val mainRequest = chain.request()


            // if response code is 401 or 403, 'mainRequest' has encountered authentication error
            if (mainResponse.code() === 401 || mainResponse.code() === 400 || mainResponse.code() === 403) {
               // val authKey = getAuthorizationHeader(session.getEmail(), session.getPassword())
                // request to login API to get fresh token
                // synchronously calling login API

              Timber.e("error happened!")

               /** val loginResponse = apiService.loginAccount(authKey).execute()

                if (loginResponse.isSuccessful()) {
                    // login request succeed, new token generated
                    val authorization = loginResponse.body()
                    // save the new token
                    session.saveToken(authorization!!.getToken())
                    // retry the 'mainRequest' which encountered an authentication error
                    // add new token into 'mainRequest' header and request again
                    val builder = mainRequest.newBuilder().header("Authorization", session.getToken())
                        .method(mainRequest.method(), mainRequest.body())
                    mainResponse = chain.proceed(builder.build())
                }*/
            //}



            }

        return mainResponse
    }


}