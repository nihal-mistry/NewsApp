package com.nihalmistry.newsapp.data

import android.content.SharedPreferences
import com.nihalmistry.newsapp.data.api.NewsApi
import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.utils.Constants
import com.nihalmistry.newsapp.utils.Result
import com.nihalmistry.newsapp.utils.safeApiCall
import java.io.IOException

// NewsRepository implementation
// Uses newsApi to fetch data from newsapi.org and SharedPreferences to read current country setting
class NewsRepositoryImpl(val newsApi: NewsApi, val prefs: SharedPreferences): NewsRepository {

    // Uses safeApiCall method to make api call and safely handle api response / errors,
    // Returns Result.Success / Result.Failure
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