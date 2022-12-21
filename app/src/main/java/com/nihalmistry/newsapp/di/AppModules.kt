package com.nihalmistry.newsapp.di

import com.nihalmistry.newsapp.data.NewsApiClient
import org.koin.dsl.module

val appModule = module {
    single { NewsApiClient.newsApi }
}