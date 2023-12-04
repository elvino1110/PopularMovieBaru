package com.example.popularmovie.di

import com.example.popularmovie.core.domain.usecase.MovieInteractor
import com.example.popularmovie.core.domain.usecase.MovieUseCase
import com.example.popularmovie.detail.DetailViewModel
import com.example.popularmovie.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor) : MovieUseCase

}*/

val useCaseModule = module {
    factory<MovieUseCase> {
        MovieInteractor(get())
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}