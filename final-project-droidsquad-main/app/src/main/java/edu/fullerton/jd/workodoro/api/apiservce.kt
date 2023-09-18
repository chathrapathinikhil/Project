package edu.fullerton.jd.workodoro.api

import edu.fullerton.jd.workodoro.PostModel
import edu.fullerton.jd.workodoro.QuoteModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Apiservce {
    @GET("/quotes/{id}")
    suspend fun getQuote(@Path("id")number:Int): Response<QuoteModel>
}