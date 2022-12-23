package com.nihalmistry.newsapp

import android.app.Application
import com.nihalmistry.newsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initializes the Koin dependency injection framework and loads modules defined.
        startKoin {
            androidContext(this@NewsApplication)
            modules(appModule)
        }
    }
}