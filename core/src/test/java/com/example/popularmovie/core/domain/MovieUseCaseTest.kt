package com.example.popularmovie.core.domain

import com.example.popularmovie.core.data.Resource
import com.example.popularmovie.core.data.source.local.entity.MovieEntity
import com.example.popularmovie.core.domain.model.Movie
import com.example.popularmovie.core.domain.repository.IMovieRepository
import com.example.popularmovie.core.domain.usecase.MovieInteractor
import com.example.popularmovie.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieUseCaseTest {

    private lateinit var movieUseCase: MovieUseCase

    @Mock private lateinit var movieRepository: IMovieRepository

    @Before
    fun setUp() {
        movieUseCase = MovieInteractor(movieRepository)
    }
    @Test
    fun `get all popular movie`() = runBlocking{
        val dummyData = listOf(Movie(
            overview = "Recently fired and desperate for work, a troubled young man named Mike agrees to take a position as a night security guard at an abandoned theme restaurant: Freddy Fazbear's Pizzeria. But he soon discovers that nothing at Freddy's is what it seems.",
            originalLanguage = "en",
            originalTitle = "Five Nights at Freddy's",
            video = false,
            title = "Five Nights at Freddy's",
            posterPath = "/A4j8S6moJS2zNtRR8oWF08gRnL5.jpg",
            backdropPath = "/t5zCBSB5xMDKcDqe91qahCOUYVV.jpg",
            releaseDate = "2023-10-25",
            popularity = 7124.811,
            voteAverage = 8.466,
            id = 507089,
            adult = false,
            voteCount = 522,
            isFavorite = false
        ))
        val resource = Resource.Success(dummyData)

        Mockito.`when`(movieRepository.getAllPopularMovie()).thenReturn(flowOf(resource))

        val result = movieUseCase.getAllPopularMovie().first()

        Mockito.verify(movieRepository).getAllPopularMovie()

        assert(result is Resource.Success)
        assert((result as Resource.Success).data == dummyData)
    }
}