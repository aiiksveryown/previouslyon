package com.aiiksveryown.previouslyon.feature_tv_show.data.response

import com.aiiksveryown.previouslyon.feature_tv_show.data.api.search.SearchTvShow

data class TvShowSearchResponse(
    val results: List<SearchTvShow>
)