package com.nihalmistry.newsapp.data

import android.content.SharedPreferences
import com.nihalmistry.newsapp.data.api.NewsApi
import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.utils.Constants
import com.nihalmistry.newsapp.utils.Result
import com.nihalmistry.newsapp.utils.safeApiCall
import java.io.IOException

class NewsRepositoryImpl(val newsApi: NewsApi, val prefs: SharedPreferences): NewsRepository {

    override suspend fun getTopHeadlines(): Result<List<NewsArticle>> = safeApiCall {
        val country = prefs.getString(Constants.COUNTRY_KEY, "us")
        val response = newsApi.getTopHeadlines(country!!, Constants.NEWS_API_KEY)
        if (response.isSuccessful) {
            val articles = response.body()?.articles ?: listOf()
            return@safeApiCall Result.Success(articles)
        }
        return@safeApiCall Result.Error(IOException("Error : ${response.code()} ${response.message()}"))
    }
}