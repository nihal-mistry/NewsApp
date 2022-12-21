package com.nihalmistry.newsapp.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class NewsArticle(
    @JsonProperty("source") var source: Source? = Source(),
    @JsonProperty("author") var author: String? = null,
    @JsonProperty("title") var title: String? = null,
    @JsonProperty("description") var description: String? = null,
    @JsonProperty("url") var url: String? = null,
    @JsonProperty("urlToImage") var urlToImage: String? = null,
    @JsonProperty("publishedAt") var publishedAt: String? = null,
    @JsonProperty("content") var content: String? = null
) : java.io.Serializable