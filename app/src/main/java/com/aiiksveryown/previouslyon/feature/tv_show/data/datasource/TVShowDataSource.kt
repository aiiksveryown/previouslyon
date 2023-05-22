package com.aiiksveryown.previouslyon.feature.tv_show.data.datasource

import com.aiiksveryown.previouslyon.feature.tv_show.data.api.HomeApi
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.detail.TVShow

import javax.inject.Inject

class TVShowDataSource @Inject constructor(
    private val service: HomeApi
) : BasePagingSource<TVShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShow> {
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getAllTvShows(pageNumber)

            LoadResult.Page(
                data = response.body()?.items as List<TVShow>,
                prevKey = null,
                nextKey = response.body()?.page?.plus(1)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}