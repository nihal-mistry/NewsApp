package com.nihalmistry.newsapp.data.api

import com.nihalmistry.newsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object NewsApiClient {

    private const val BASE_URL = "https://newsapi.org/"

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    )

    private val jacksonConverter = JacksonConverterFactory.create()

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(jacksonConverter)
        .build()

    val newsApi: NewsApi by lazy {
        retrofit.create(NewsApi::class.java)
    }
}