package com.aiiksveryown.previouslyon.ui.shows_lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.Media
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.daily_trending.DailyTrending
import com.aiiksveryown.previouslyon.feature.tv_show.data.repository.ShowsListRepository
import com.aiiksveryown.previouslyon.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsListViewModel @Inject constructor(
    private val repository: ShowsListRepository
) : ViewModel() {

    private val _showList = MutableStateFlow(CategoryUIState())
    val showList: StateFlow<CategoryUIState> get() = _showList

    init {
        viewModelScope.launch {
            val result = repository.getPaginatedTvShows()
            _showList.update {
                it.copy(
                    media = result.map {
                        it.map {
                            Media(
                                mediaID = it.mediaID,
                                mediaImage = it.mediaImage,
                                mediaType = it.mediaType,
                                mediaName = it.mediaName,
                                mediaDate = it.mediaDate,
                                mediaRate = it.mediaRate
                            )
                        }
                    }
                )
            }
        }
    }

}