package com.aiiksveryown.previouslyon.feature_tv_show.data.response

import com.aiiksveryown.previouslyon.feature_tv_show.data.api.detail.Cast

data class TvShowCreditsResponse(
    val cast: List<Cast>
)