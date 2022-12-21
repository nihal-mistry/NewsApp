package com.nihalmistry.newsapp.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class GetTopHeadlinesResponse(
    @JsonProperty("status") var status: String? = null,
    @JsonProperty("totalResults") var totalResults: Int? = null,
    @JsonProperty("articles") var articles: ArrayList<NewsArticle> = arrayListOf()
) : java.io.Serializable