package com.aiiksveryown.previouslyon.feature_movie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiiksveryown.previouslyon.feature_movie.data.database.entity.MovieEntity
import com.aiiksveryown.previouslyon.feature_movie.data.api.search.SearchMovie
import com.aiiksveryown.previouslyon.feature_movie.data.response.MovieTvShowCreditsResponse
import com.aiiksveryown.previouslyon.feature_movie.data.response.MovieDetailResponse
import com.aiiksveryown.previouslyon.feature_movie.data.repository.MovieRepository
import com.aiiksveryown.previouslyon.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieSearch = MutableLiveData<Resource<List<SearchMovie>>>()
    val movieSearch : LiveData<Resource<List<SearchMovie>>> get() = _movieSearch

    private val _movieDetail = MutableLiveData<Resource<MovieDetailResponse>>()
    val movieDetail : LiveData<Resource<MovieDetailResponse>> get() = _movieDetail

    private val _movieCredits = MutableLiveData<Resource<MovieTvShowCreditsResponse>>()
    val movieCredits : LiveData<Resource<MovieTvShowCreditsResponse>> get() = _movieCredits

    val getAllMovies : LiveData<List<MovieEntity>> = movieRepository.getAllMovies()

    fun getMovieById(movieId: Int) : LiveData<MovieEntity> {
        return movieRepository.getMovieById(movieId)
    }

    fun insertMovie(movieEntity: MovieEntity) {
        viewModelScope.launch {
            movieRepository.insertMovie(movieEntity)
        }
    }

    fun deleteMovie(movieEntity: MovieEntity) {
        viewModelScope.launch {
            movieRepository.deleteMovie(movieEntity)
        }
    }

    fun movieCredit(movieId: Int) {
        viewModelScope.launch {
            _movieCredits.value = Resource.Loading()
            try {
                val response = movieRepository.movieCredits(movieId)
                _movieCredits.value = response.data?.let { Resource.Success(it) }
            } catch (e : Exception) {
                _movieCredits.value = Resource.Failure("Failed to fetch movie credits ${e.localizedMessage}")
            }
        }
    }

    fun movieDetail(movieId : Int) {
        viewModelScope.launch {
            _movieDetail.value = Resource.Loading()
            try {
                val response = movieRepository.movieDetails(movieId)
                 Log.e("MovieViewModel", "movieDetail: ${response.data}")
                _movieDetail.value = response.data?.let { Resource.Success(it) }
            } catch (e : Exception) {
                _movieDetail.value = Resource.Failure("Failed to fetch movie details ${e.localizedMessage}")
            }
        }
    }

    fun movieSearch(movieName : String) {
        viewModelScope.launch {
            _movieSearch.value = Resource.Loading()
            try {
                val response = movieRepository.searchMovie(movieName)

                _movieSearch.value = response.data?.let { Resource.Success(it) }
            } catch (e : Exception) {
                Log.e("MovieViewModel", "movieSearch: ${(e as HttpException).response()?.errorBody()}")
                _movieSearch.value = Resource.Failure("Failed to fetch movie list ${e.localizedMessage}")
            }
        }
    }

//    fun movieSearch(movieName : String) {
//        viewModelScope.launch {
//            _movieSearch.value = Resource.Loading()
//            try {
//                val response = movieRepository.searchMovie(movieName)
//                if(response.isSuccessful) {
//                    _movieSearch.value = response.body()?.let { Resource.Success(it) }
//                } else {
//                    val error = response.errorBody()?.string()
//                    _movieSearch.value = Resource.Failure("Failed to fetch movie list: $error")
//                }
//            } catch (e : Exception) {
//                _movieSearch.value = Resource.Failure("Failed to fetch movie list ${e.localizedMessage}")
//            }
//        }
//    }

}