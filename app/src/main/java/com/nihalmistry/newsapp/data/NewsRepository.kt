package com.nihalmistry.newsapp.data

import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.utils.Result

// Interface to define NewsRepository functions
interface NewsRepository {
    suspend fun getTopHeadlines(): Result<List<NewsArticle>>
}