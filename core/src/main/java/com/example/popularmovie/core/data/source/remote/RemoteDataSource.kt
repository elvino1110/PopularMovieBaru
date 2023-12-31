package com.example.popularmovie.core.data.source.remote

import android.util.Log
import com.example.popularmovie.core.data.source.remote.network.ApiResponse
import com.example.popularmovie.core.data.source.remote.network.ApiService
import com.example.popularmovie.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val apiService: ApiService){

    suspend fun getAllPopularMovie() : Flow<ApiResponse<List<ResultsItem>>> {

        return flow {
            try {
                val response = apiService.getListPopularMovie()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getAllSearchMovie(query: String) : Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            try {
                val response = apiService.getSearchMovie(query = query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}