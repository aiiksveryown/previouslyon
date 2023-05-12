package com.aiiksveryown.previouslyon.feature_movie.data.response

import com.aiiksveryown.previouslyon.feature_movie.data.api.search.SearchMovie

data class MovieSearchResponse(
    val results: List<SearchMovie>
)