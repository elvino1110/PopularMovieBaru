package com.example.popularmovie

import android.app.Application
import com.example.popularmovie.core.di.databaseModule
import com.example.popularmovie.core.di.networkModule
import com.example.popularmovie.core.di.repositoryModule
import com.example.popularmovie.di.useCaseModule
import com.example.popularmovie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/*
@HiltAndroidApp
open class MyApplication : Application()*/
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    useCaseModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}