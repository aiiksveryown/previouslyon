package com.aiiksveryown.previouslyon.feature.tv_show.data.response

import com.aiiksveryown.previouslyon.feature.tv_show.data.api.detail.Cast

data class TvShowCreditsResponse(
    val cast: List<Cast>
)