package com.nihalmistry.newsapp.data.api

import com.nihalmistry.newsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

// Singleton object for creating NewsApi instance
object NewsApiClient {

    private const val BASE_URL = "https://newsapi.org/"

    // HttpLogginInterceptor instance for logging api request/responses
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    )

    // Jackson json converter
    private val jacksonConverter = JacksonConverterFactory.create()

    // OkHttpClient instance with network interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    // Retrofit object to create API instance
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(jacksonConverter)
        .build()

    val newsApi: NewsApi by lazy {
        retrofit.create(NewsApi::class.java)
    }
}