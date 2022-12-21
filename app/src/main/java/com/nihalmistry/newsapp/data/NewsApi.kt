package com.nihalmistry.newsapp.data

import com.nihalmistry.newsapp.data.models.GetTopHeadlinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Response<GetTopHeadlinesResponse>

}