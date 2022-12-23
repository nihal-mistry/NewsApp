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
    // Create single instance of SharedPreferences for dependency injection
    single(named(USER_PREFS)) {
        androidContext().getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
    }

    // Creates single instance of NewsApi object
    single { NewsApiClient.newsApi }

    // Creates single instance of CoroutinesDispatcherProvider
    single { CoroutinesDispatcherProvider() }

    // Creates single instance of NewsRepositoryImpl for injecting in ViewModel
    single<NewsRepository> { NewsRepositoryImpl(get(), get(named(USER_PREFS))) }

    // Uses factory to create viewModel instance per activity/fragment instance
    viewModel { NewsListViewModel(get(), get()) }

}

const val USER_PREFS = "app_prefs"