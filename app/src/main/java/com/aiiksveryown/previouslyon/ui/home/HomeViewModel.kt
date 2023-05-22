package com.aiiksveryown.previouslyon.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.daily_trending.DailyTrending
import com.aiiksveryown.previouslyon.feature.tv_show.data.repository.HomeRepository
import com.aiiksveryown.previouslyon.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): ViewModel(){

    private val _dailyTrending = MutableLiveData<Resource<List<DailyTrending>>>()
    val dailyTrending : LiveData<Resource<List<DailyTrending>>> get() = _dailyTrending

    init {
        getDailyTrending()
    }

    fun getDailyTrending() {
        viewModelScope.launch {
            _dailyTrending.value = Resource.Loading()
            try {
                val response = repository.getDailyTrending()
                _dailyTrending.value = response.data?.items?.let { Resource.Success(it) }
                    ?: Resource.Failure("Failed to fetch movie list")
            } catch (e: Exception) {
                _dailyTrending.value =
                    Resource.Failure("Failed to fetch movie list ${e.localizedMessage}")
            }
        }
    }
}