package com.example.popularmovie.core.data.source.local

import com.example.popularmovie.core.data.source.local.entity.MovieEntity
import com.example.popularmovie.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

/*@Singleton*/
class LocalDataSource/* @Inject constructor*/(private val movieDao: MovieDao){

    fun getAllPopularMovie(): Flow<List<MovieEntity>> = movieDao.getAllPopularMovie()

    fun getFavoritePopularMovie(): Flow<List<MovieEntity>> = movieDao.getFavoritePopularMovie()

    suspend fun insertFavoritePopularMovie(movieList: List<MovieEntity>) = movieDao.insertFavoritePopularMovie(movieList)

    fun setFavoritePopularMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoritePopularMovie(movie)
    }
}