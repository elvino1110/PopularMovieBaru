package com.example.popularmovie.core.domain.usecase

import com.example.popularmovie.core.domain.model.Movie
import com.example.popularmovie.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllPopularMovie() = movieRepository.getAllPopularMovie()

    override fun getFavoritePopularMovie() = movieRepository.getFavoritePopularMovie()

    override fun setFavoritePopularMovie(movie: Movie, state: Boolean) = movieRepository.setFavoritePopularMovie(movie, state)

    override fun getSearchMovie(query: String) = movieRepository.getSearchMovie(query)

}