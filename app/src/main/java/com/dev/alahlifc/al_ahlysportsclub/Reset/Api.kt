package com.dev.alahlifc.al_ahlysportsclub.Reset

import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.models.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

private var retrofit: Retrofit? = null

interface Api {

    @FormUrlEncoded
    @POST("/api/v1/register")
    fun register(
        @Field("email") email: String,
        @Field("display_name") display_name: String,
        @Field("password") password: String,
        @Field("country") country: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("reg_site") reg_site: String,
        @Field("phone") phone: String,
        @Field("fcm_token") fcm_token: String
    ): Call<mRegisterLogin>


    @FormUrlEncoded
    @POST("/api/v1/login/email" )
    fun login(
        @Field("email_user_name") email_user_name : String,
        @Field("password") password: String,
        @Field("fcm_token") fcm_token: String
    ): Call<mRegisterLogin>



    @POST("/api/v1/about" )
    fun about(): Call<mAbout>


    @GET("/api/v1/profile" )
    fun myProfile(
        @Header("access-token") access_token : String
    ): Call<mMyProfile>

    @FormUrlEncoded
    @POST("/api/v1/news" )
    fun news(
        @Field("num_page") num_page: String,
        @Field("limit") limit: String
    ): Call<mNews>


    @FormUrlEncoded
    @POST("/api/v1/videos" )
    fun videos(
        @Field("num_page") num_page: String,
        @Field("limit") limit: String,
        @Field("lang") lang: String
    ): Call<mVideos>


    @FormUrlEncoded
    @POST("/api/v1/albums" )
    fun albums(
        @Field("num_page") num_page: String,
        @Field("limit") limit: String,
        @Field("lang") lang: String
    ): Call<mAlbum>


    @FormUrlEncoded
    @POST("/api/v1/update_profile" )
    fun editProfile(
        @Header("access-token") access_token : String,
        @Field("email") email: String,
        @Field("display_name") display_name: String,
        @Field("phone") phone : String,
        @Field("image") image: String,
        @Field("country") country: String,
        @Field("city") city : String,
        @Field("state") state: String,
        @Field("gender") gender: String
    ): Call<mEditProfile>


    @FormUrlEncoded
    @POST("/api/v1/uploadImage" )
    fun uploadImage(
        @Header("access-token") access_token : String,
        @Field("image") image: String
    ): Call<mUploadImage>


    @POST("/api/v1/logout" )
    fun  logout(
        @Header("access-token") access_token : String
    ): Call<mLogout>


    @FormUrlEncoded
    @POST("/api/v1/home" )
    fun fillHome(
        @Field("lang") lang: String,
        @Field("limit") limit: String
    ): Call<mHome>


    @POST("/api/v1/contact_us" )
    fun contactUs(): Call<mContactUs>


    @FormUrlEncoded
    @POST("/api/v1/add_contact_us" )
    fun addContactMessage(
        @Header("access-token") access_token : String,
        @Field("content") content : String,
        @Field("user_name") user_name: String,
        @Field("user_email") user_email: String
    ): Call<mAddContact>



    @FormUrlEncoded
    @POST("/api/v1/product_categories" )
    fun ProductCategories(
        @Header("access-token") access_token : String,
        @Field("lang") lang: String,
        @Field("limit") limit: String,
        @Field("num_page") num_page: String
    ): Call<mProductCategories>


    @FormUrlEncoded
    @POST("/api/v1/product_category" )
    fun getItemsInCategory(
        @Field("lang") lang: String,
        @Field("cat_link") cat_link : String,
        @Field("num_page") num_page : String,
        @Field("val_sort") val_sort : String
    ): Call<mItemsInCategory>


    @FormUrlEncoded
    @POST("/api/v1/matches" )
    fun getMatches(
        @Field("lang") lang: String,
        @Field("limit") limit: String,
        @Field("num_page") num_page: String,
        @Field("type") type: String
    ): Call<mMatches>



    @POST("/api/v1/product_cart" )
    fun getCart(
        @Header("access-token") access_token : String
    ): Call<mCart>


    @FormUrlEncoded
    @POST("api/v1/product/addupdate_cart" )
    fun AddUpdateCart(
        @Header("access-token") access_token : String,
        @Field("link") link: String,
        @Field("quantity") quantity: String,
        @Field("weight") weight: String,
        @Field("color") color: String,
        @Field("name_print") name_print: String,
        @Field("fees[]")  fees : MutableList<String>,
        @Field("lang") lang: String
    ): Call<mAddUpdateCart>

    @FormUrlEncoded
    @POST("api/v1/product/delete_cart" )
    fun DeleteCart(
        @Header("access-token") access_token : String,
        @Field("link") link: String,
        @Field("lang") lang: String
    ): Call<mDeleteCart>


    @FormUrlEncoded
    @POST("api/v1/products" )
    fun products(
        @Field("num_page") num_page : String,
        @Field("limit") limit: String,
        @Field("lang") lang: String
    ): Call<mProduct>



    @FormUrlEncoded
    @POST("api/v1/product/checkout" )
    fun Checkout(
        @Header("access-token") access_token : String,
        @Field("lang") lang: String
    ): Call<mCheckout>


    @FormUrlEncoded
    @POST("api/v1/product/resultcallback" )
    fun ResultCallback(
        @Header("access-token") access_token : String,
        @Field("checkout_id") checkout_id  : String,
        @Field("lang") lang: String
    ): Call<mResultCallback>



    @FormUrlEncoded
    @POST("/api/v1/champions" )
    fun Champions(
        @Field("lang") lang: String
    ): Call<mChampions>


    @FormUrlEncoded
    @POST("/api/v1/audience" )
    fun Audience(
        @Field("lang") lang: String
    ): Call<mAudience>

    @FormUrlEncoded
    @POST("/api/v1/calendar" )
    fun Calendar(
        @Field("lang") lang: String
    ): Call<mCalendar>


    @FormUrlEncoded
    @POST("/api/v1/albums/single" )
    fun SubAlbum(
        @Field("lang") lang: String,
        @Field("albums_link") albums_link : String
    ): Call<mSubAlbum>


    @FormUrlEncoded
    @POST("/api/v1/products/single" )
    fun SingleProduct(
        @Field("lang") lang: String,
        @Field("products_link") products_link  : String
    ): Call<mProductSingle>


    @FormUrlEncoded
    @POST("/api/v1/add_comment" )
    fun AddComment(
        @Header("access-token") access_token : String,
        @Field("lang") lang: String,
        @Field("content") content : String,
        @Field("link") link  : String,
        @Field("user_email") user_email  : String,
        @Field("user_name") user_name  : String,
        @Field("type") type  : String
    ): Call<mAddComment>

    @FormUrlEncoded
    @POST("/api/v1/add_comment" )
    fun AddRate(
        @Header("access-token") access_token : String,
        @Field("lang") lang: String,
        @Field("content") content : String,
        @Field("link") link  : String,
        @Field("user_email") user_email  : String,
        @Field("user_name") user_name  : String,
        @Field("type") type  : String,
        @Field("rate") rate  : String
    ): Call<mAddComment>


    @FormUrlEncoded
    @POST("/api/v1/add_comment" )
    fun AddReply(
        @Header("access-token") access_token : String,
        @Field("lang") lang: String,
        @Field("content") content : String,
        @Field("link") link  : String,
        @Field("link_parent") link_parent  : String,
        @Field("user_email") user_email  : String,
        @Field("user_name") user_name  : String,
        @Field("type") type  : String
    ): Call<mAddComment>


    @FormUrlEncoded
    @POST("/api/v1/teams" )
    fun Teams(
        @Field("lang") lang: String
    ): Call<mTeams>


    @FormUrlEncoded
    @POST("/api/v1/subteams" )
    fun FootballTeam(
        @Field("lang") lang: String,
        @Field("team_link") team_link : String
    ): Call<mFootballTeam>



    @FormUrlEncoded
    @POST("/api/v1/comments" )
    fun Comments(
        @Header("access-token") access_token : String,
        @Field("link") link  : String,
        @Field("type") type : String,
        @Field("num_page") num_page : String,
        @Field("limit") limit : String
    ): Call<mComments>


    @FormUrlEncoded
    @POST("/api/v1/add_del_like" )
    fun LikeOrUnLike(
        @Header("access-token") access_token : String,
        @Field("link") link  : String,
        @Field("type") type : String
    ): Call<mAddDeleteLike>


    @FormUrlEncoded
    @POST("/api/v1/delete_comment" )
    fun DeleteComment(
        @Header("access-token") access_token : String,
        @Field("comment_link") link  : String,
        @Field("type") type : String
    ): Call<mDeleteComment>




    companion object Factory {

        operator fun invoke(): Api {

            if (retrofit == null) {
                        val logging = HttpLoggingInterceptor()
                        logging.level = HttpLoggingInterceptor.Level.BODY

                        val client = OkHttpClient.Builder()
                            .connectTimeout(1, TimeUnit.MINUTES)
                            .writeTimeout(1, TimeUnit.MINUTES)
                            .readTimeout(1, TimeUnit.MINUTES)
                            .addInterceptor(logging)
                           .authenticator(SupportInterceptor())
                            .build()

                        retrofit = Retrofit.Builder()
                            .baseUrl(Constants.baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build()

            }
            return retrofit!!.create(Api::class.java)
        }
    }
}