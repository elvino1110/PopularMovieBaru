package com.example.popularmovie.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.popularmovie.core.domain.usecase.MovieUseCase

/*@HiltViewModel*/
class MainViewModel /*@Inject constructor*/(private val movieUseCase: MovieUseCase) : ViewModel() {
    val movie = movieUseCase.getAllPopularMovie().asLiveData()

    fun searchMovie(query: String) =
        movieUseCase.getSearchMovie(query).asLiveData()
}