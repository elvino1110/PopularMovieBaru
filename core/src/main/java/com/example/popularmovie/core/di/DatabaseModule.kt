package com.example.popularmovie.core.di

import androidx.room.Room
import com.example.popularmovie.core.data.source.local.room.MovieDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single {
        val key : ByteArray = SQLiteDatabase.getBytes("movie".toCharArray())
        val factory = SupportFactory(key)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "PopularMovieNew.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}