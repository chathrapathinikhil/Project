package edu.fullerton.jd.workodoro.api

import edu.fullerton.jd.workodoro.utils.Constants.Companion.BASE_URL
import edu.fullerton.jd.workodoro.utils.Constants.Companion.BASE_URL1
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .baseUrl(BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: Apiservce by lazy {
        retrofit.create(Apiservce::class.java)
    }
}