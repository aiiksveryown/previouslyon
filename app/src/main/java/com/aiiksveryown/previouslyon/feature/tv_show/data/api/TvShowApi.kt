package com.aiiksveryown.previouslyon.feature.tv_show.data.api

import com.aiiksveryown.previouslyon.feature.tv_show.data.response.TvShowCreditsResponse
import com.aiiksveryown.previouslyon.feature.tv_show.data.response.TvShowDetailResponse
import com.aiiksveryown.previouslyon.feature.tv_show.data.response.TvShowSearchResponse
import com.aiiksveryown.previouslyon.feature.tv_show.data.response.TvShowSeasonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale

interface TvShowApi {

    @GET("search/tv")
    suspend fun searchTvShow(
        @Query("query") query : String,
        @Query("language") language : String = Locale.getDefault().language
    ) : Response<TvShowSearchResponse>

    @GET("tv/{tv_id}")
    suspend fun detailTvShow(
        @Path("tv_id") id : Int,
        @Query("language") language: String = Locale.getDefault().language
    ) : Response<TvShowDetailResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun tvShowCredits(
        @Path("tv_id") id : Int
    ) : Response<TvShowCreditsResponse>

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun tvShowSeasons(
        @Path("tv_id") tvId : Int,
        @Path("season_number") seasonNumber : Int,
        @Query("language") language: String = Locale.getDefault().language
    ) : Response<TvShowSeasonResponse>
}