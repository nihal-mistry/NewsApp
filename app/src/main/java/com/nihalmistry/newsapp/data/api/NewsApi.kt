package com.nihalmistry.newsapp.data.api

import com.nihalmistry.newsapp.data.api_models.GetTopHeadlinesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// interface for defining retrofit API calls
interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Response<GetTopHeadlinesResponse>

}