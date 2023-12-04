package com.example.popularmovie.core.domain.usecase

import com.example.popularmovie.core.data.Resource
import com.example.popularmovie.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getAllPopularMovie() : Flow<Resource<List<Movie>>>
    fun getFavoritePopularMovie() : Flow<List<Movie>>
    fun setFavoritePopularMovie(movie: Movie, state: Boolean)
    fun getSearchMovie(query: String) : Flow<Resource<List<Movie>>>

}