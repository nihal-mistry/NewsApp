package com.nihalmistry.newsapp.data

import com.nihalmistry.newsapp.data.api_models.NewsArticle
import com.nihalmistry.newsapp.utils.Result

interface NewsRepository {
    suspend fun getTopHeadlines(): Result<List<NewsArticle>>
}