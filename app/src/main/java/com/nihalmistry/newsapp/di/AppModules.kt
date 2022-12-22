package com.nihalmistry.newsapp.di

import android.content.Context
import com.nihalmistry.newsapp.data.NewsRepository
import com.nihalmistry.newsapp.data.NewsRepositoryImpl
import com.nihalmistry.newsapp.data.api.NewsApiClient
import com.nihalmistry.newsapp.ui.NewsListViewModel
import com.nihalmistry.newsapp.utils.CoroutinesDispatcherProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named(USER_PREFS)) {
        androidContext().getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
    }
    single { NewsApiClient.newsApi }
    single { CoroutinesDispatcherProvider() }
    single<NewsRepository> { NewsRepositoryImpl(get(), get(named(USER_PREFS))) }

    viewModel { NewsListViewModel(get(), get()) }

}

const val USER_PREFS = "app_prefs"