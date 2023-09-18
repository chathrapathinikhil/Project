package edu.fullerton.jd.workodoro.repository

import edu.fullerton.jd.workodoro.PostModel
import edu.fullerton.jd.workodoro.QuoteModel
import edu.fullerton.jd.workodoro.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class Repository {
    suspend fun getQuote(number: Int): Response<QuoteModel> {
        return RetrofitInstance.api.getQuote(number)
    }
}