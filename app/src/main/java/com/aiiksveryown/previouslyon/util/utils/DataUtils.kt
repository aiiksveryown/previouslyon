package com.aiiksveryown.previouslyon.util.utils

import com.aiiksveryown.previouslyon.feature_tv_show.data.api.search.SearchTvShow
import com.aiiksveryown.previouslyon.feature_tv_show.data.database.entity.TvShowEntity

fun SearchTvShow.toTvShowEntity(): TvShowEntity {
    return TvShowEntity(
        id = this.id,
        tvShowStatus = "",
        tvShowOverview = this.overview,
        tvShowName = this.name,
        tvShowBackdropPath = this.backdrop_path.toString(),
        tvShowPosterPath = this.poster_path.toString(),
        tvShowVoteAverage = this.vote_average.formatVoteAverage(),
        tvShowFirstAirDate = this.first_air_date,
        tvShowEpisodeRun = 0
    )
}

fun TvShowEntity.toSearchTv(): SearchTvShow {
    return SearchTvShow(
        backdrop_path = this.tvShowBackdropPath,
        first_air_date = this.tvShowFirstAirDate,
        id = this.id,
        name = this.tvShowName,
        overview = this.tvShowOverview,
        popularity = 0.0,
        poster_path = this.tvShowPosterPath,
        vote_average = this.tvShowVoteAverage.replace(",", ".").toDouble(),
    )
}
