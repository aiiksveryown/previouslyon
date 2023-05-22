package com.aiiksveryown.previouslyon.feature.tv_show.data.api

import com.aiiksveryown.previouslyon.feature.tv_show.data.api.daily_trending.DailyTrending
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.detail.TVShow
import com.aiiksveryown.previouslyon.feature.tv_show.data.response.TvShowListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("trending/all/day")
    suspend fun getDailyTrending(): Response<TvShowListResponse<DailyTrending>>

    @GET("discover/tv")
    suspend fun getAllTvShows(@Query("page") page: Int = 1): Response<TvShowListResponse<TVShow>>

}