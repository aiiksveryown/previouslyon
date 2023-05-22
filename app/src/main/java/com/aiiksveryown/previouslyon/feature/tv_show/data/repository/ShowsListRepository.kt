package com.aiiksveryown.previouslyon.feature.tv_show.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.Media
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.detail.TVShow
import com.aiiksveryown.previouslyon.feature.tv_show.data.datasource.TVShowDataSource
import com.aiiksveryown.previouslyon.feature.tv_show.data.datasource.TVShowMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowsListRepository @Inject constructor(
    private val dataSource: TVShowDataSource,
    private val tvShowMapper: TVShowMapper
) {
    private val config = PagingConfig(pageSize = 100, prefetchDistance = 5, enablePlaceholders = false)

    suspend fun getShowList() : Pager<Int, TVShow>{
       return  Pager(
            config = config,
            pagingSourceFactory = { dataSource })
    }

    suspend fun getPaginatedTvShows(): Flow<PagingData<Media>> {
        return wrapper(::getShowList, tvShowMapper::map)
    }

    private suspend fun <T : Any> wrapper(
        data: suspend () -> Pager<Int, T>,
        mapper: (T) -> Media,
    ): Flow<PagingData<Media>> {
        return data().flow.map { pager -> pager.map { mapper(it) } }
    }

}
