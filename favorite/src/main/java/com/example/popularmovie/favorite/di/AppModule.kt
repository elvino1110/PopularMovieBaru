package com.example.popularmovie.favorite.di

import com.example.popularmovie.core.utils.DataMapper
import com.example.popularmovie.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favouriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}