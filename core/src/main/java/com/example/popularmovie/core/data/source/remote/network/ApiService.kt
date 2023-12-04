package com.example.popularmovie.core.data.source.remote.network

import com.example.popularmovie.core.data.source.remote.response.ListPopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getListPopularMovie(
        @Header("Authorization") key : String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYTBiMWY3ZjMxYjI4ZDczYzYwZmU1YjY5MDVlYTVmNiIsInN1YiI6IjY1MjM3MjE3MDcyMTY2MDBjNTZiZDc5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Sl8tspsqbgXwxxxxOTM1t3sNrriayXK-Ho842EwFPOw"
    ) : ListPopularMovieResponse

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Header("Authorization") key : String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjYTBiMWY3ZjMxYjI4ZDczYzYwZmU1YjY5MDVlYTVmNiIsInN1YiI6IjY1MjM3MjE3MDcyMTY2MDBjNTZiZDc5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Sl8tspsqbgXwxxxxOTM1t3sNrriayXK-Ho842EwFPOw",
        @Query("query") query: String
    ) : ListPopularMovieResponse

}