package com.nihalmistry.newsapp.data.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Source(
    @JsonProperty("id") var id: String? = null,
    @JsonProperty("name") var name: String? = null
) : java.io.Serializable
