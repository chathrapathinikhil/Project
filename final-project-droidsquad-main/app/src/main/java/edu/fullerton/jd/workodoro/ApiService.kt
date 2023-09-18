package edu.fullerton.jd.workodoro

import android.provider.ContactsContract
import edu.fullerton.jd.workodoro.PostModel
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("/posts")
    fun getQuotes(): Call<MutableList<PostModel>>

    @POST("/posts")
    fun createPost(@Body post: PostModel): Call<PostModel>

    @FormUrlEncoded
    @POST("/posts")
    fun createPost(
        @Field("userId") userId: Int?,
        @Field("id") id: Int?,
        @Field("title") title: String?,
        @Field("body") body: String?
    ): Call<PostModel>

    // PUT request
    @PUT("/posts/{id}")
    fun updatePost(@Path("id") postId: Int, @Body post: PostModel): Call<PostModel>

    @GET("postModel")
    fun getData(): Call<List<PostModel>>

    @POST("postModel")
    fun sendData(@Body data: PostModel): Call<Void>

}