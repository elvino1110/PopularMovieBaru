package com.example.popularmovie.core.data.source.local.room

import androidx.room.*
import com.example.popularmovie.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllPopularMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie where isFavorite = 1")
    fun getFavoritePopularMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePopularMovie(movie: List<MovieEntity>)

    @Update
    fun updateFavoritePopularMovie(movie: MovieEntity)
}