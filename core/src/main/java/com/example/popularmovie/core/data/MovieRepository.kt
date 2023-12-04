package com.example.popularmovie.core.data

import com.example.popularmovie.core.data.source.local.LocalDataSource
import com.example.popularmovie.core.data.source.remote.RemoteDataSource
import com.example.popularmovie.core.data.source.remote.network.ApiResponse
import com.example.popularmovie.core.data.source.remote.response.ResultsItem
import com.example.popularmovie.core.domain.model.Movie
import com.example.popularmovie.core.domain.repository.IMovieRepository
import com.example.popularmovie.core.utils.AppExecutors
import com.example.popularmovie.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/*@Singleton*/

class MovieRepository
/*@Inject constructor*/
(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllPopularMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllPopularMovie().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllPopularMovie()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertFavoritePopularMovie(movieList)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true
        }.asFlow()


    override fun getFavoritePopularMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoritePopularMovie().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoritePopularMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoritePopularMovie(movieEntity, state) }
    }

    override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                // Tidak ada perlu untuk mengambil data dari database lokal untuk pencarian
                return flow { emit(emptyList()) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllSearchMovie(query)

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                // Tidak perlu untuk menyimpan hasil pencarian ke database lokal
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true
        }.asFlow()

}
/*
class MovieRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository {
    override fun getAllPopularMovie(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllPopularMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> {
                return remoteDataSource.getAllPopularMovie()
            }

            override suspend fun saveCall(data: List<ResultsItem>) {
                val entity = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertFavoritePopularMovie(entity)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return true
            }

        }.asFlow()
    }

    override fun getFavoritePopularMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoritePopularMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoritePopularMovie(movie: Movie, state: Boolean) {
        val entity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setFavoritePopularMovie(entity, state)
        }
    }

    override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                // Tidak ada perlu untuk mengambil data dari database lokal untuk pencarian
                return flow { emit(emptyList()) }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getAllSearchMovie(query)

            override suspend fun saveCall(data: List<ResultsItem>) {
                val entity = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertFavoritePopularMovie(entity)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true
        }.asFlow()

}*/
