package com.example.popularmovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListPopularMovieResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem>
)