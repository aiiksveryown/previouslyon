package com.aiiksveryown.previouslyon.feature.tv_show.data.repository

import com.aiiksveryown.previouslyon.feature.tv_show.data.response.TvShowListResponse
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.HomeApi
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.daily_trending.DailyTrending
import com.aiiksveryown.previouslyon.util.network.Resource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {
    suspend fun getDailyTrending(): Resource<TvShowListResponse<DailyTrending>> {
        return try {
            val response = homeApi.getDailyTrending()
            if (response.isSuccessful) {
                val tvShowDetailResponse = response.body()
                val tvShows = tvShowDetailResponse!!
                Resource.Success(tvShows)
            } else {
                Resource.Failure("Failed to fetch tv show details")
            }
        } catch (e : Exception) {
            Resource.Failure("Failed to fetch tv show details ${e.localizedMessage}")
        }
    }
}