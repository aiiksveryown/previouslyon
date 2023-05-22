package com.aiiksveryown.previouslyon.feature.tv_show.data.datasource

import com.aiiksveryown.previouslyon.feature.tv_show.data.api.Mapper
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.Media
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.MediaType
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.detail.TVShow
import com.aiiksveryown.previouslyon.util.Constants
import javax.inject.Inject

class TVShowMapper @Inject constructor() : Mapper<TVShow, Media> {
    override fun map(input: TVShow): Media {
        return Media(
            input.id ?: 0,
            Constants.TMDB_IMAGE_ORIGINAL + input.posterPath,
            MediaType.TV_SHOW.value,
            input.originalName ?: "",
            input.firstAirDate?.substringBefore('-') ?: "",
            input.voteAverage?.toFloat() ?: 0F,
        )
    }
}