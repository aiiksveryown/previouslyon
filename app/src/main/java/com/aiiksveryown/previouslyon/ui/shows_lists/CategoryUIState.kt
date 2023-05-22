package com.aiiksveryown.previouslyon.ui.shows_lists

import androidx.paging.PagingData
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.Media
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CategoryUIState(
    val media: Flow<PagingData<Media>> = emptyFlow(),
    val isLoading: Boolean = false,
)
