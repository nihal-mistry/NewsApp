package com.nihalmistry.newsapp.utils

import com.nihalmistry.newsapp.R

class Constants {
    companion object {
        // SharedPreference Key to store country setting
        val COUNTRY_KEY = "country"

        // NewsApi.org API key
        val NEWS_API_KEY = "59c4684288e54223b7c08c88a6aa7bb2"

        // Map to store country value and corresponding flag
        // Used in showing bottom sheet for country selection
        val COUNTRY_FLAG_MAP = linkedMapOf(
            "us" to R.drawable.us_flag,
            "ca" to R.drawable.ca_flag
        )
    }
}