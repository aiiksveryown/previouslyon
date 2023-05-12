package com.aiiksveryown.previouslyon.feature_movie.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.aiiksveryown.previouslyon.feature_movie.data.api.MovieApi
import com.aiiksveryown.previouslyon.feature_movie.data.api.search.SearchMovie
import com.aiiksveryown.previouslyon.feature_movie.data.database.dao.MovieDao
import com.aiiksveryown.previouslyon.feature_movie.data.database.entity.MovieEntity
import com.aiiksveryown.previouslyon.feature_movie.data.response.MovieDetailResponse
import com.aiiksveryown.previouslyon.feature_movie.data.response.MovieTvShowCreditsResponse
import com.aiiksveryown.previouslyon.util.network.Resource
import okhttp3.ResponseBody
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao
) {

    suspend fun insertMovie(movie : MovieEntity) {
        return movieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(movie: MovieEntity) {
        return movieDao.deleteMovie(movie)
    }

    fun getAllMovies() : LiveData<List<MovieEntity>> {
        return movieDao.getAllMovies()
    }

    fun getMovieById(movieId: Int) : LiveData<MovieEntity> {
        return movieDao.getMovieById(movieId)
    }

    suspend fun searchMovie(movieName : String) : Resource<List<SearchMovie>> {
        return try {
            val response = movieApi.searchMovie(query = movieName)
            Log.e("MovieRepository", "searchMovie: ${response.body()}")
            if (response.isSuccessful) {
                val movieSearchResponse = response.body()
                val movies = movieSearchResponse!!.results
                Resource.Success(movies)
            } else {
                Resource.Failure("Failed to fetch movie list")
            }
        } catch (e : HttpException) {
            // Check type of exception
            Log.e("MovieRepository", "searchMovie - e type: ${e::class.java}")
            if (e is HttpException) {
                val responseBody = e.response()?.errorBody()
                val message = responseBody?.string()
                Log.e("MovieRepository", "searchMovie: $message")
            }
            Resource.Failure("Failed to fetch movie list ${e.localizedMessage}")
        }
    }

//    suspend fun searchMovie(movieName: String): Resource<List<SearchMovie>> {
//        return try {
//            val response = movieApi.searchMovie(query = movieName)
//            Log.e("MovieRepository", "searchMovie: ${response.body()}")
//            if (response.isSuccessful) {
//                val movieSearchResponse = response.body()
//                val movies = movieSearchResponse!!.results
//                Resource.Success(movies)
//            } else {
//                val errorBody = response.errorBody()?.string()
//                Log.e("MovieRepository", "searchMovie errorBody: $errorBody")
//                Resource.Failure("Failed to fetch movie list")
//            }
//        } catch (e: Exception) {
//            Log.e("MovieRepository", "searchMovie: ${e}")
//            Resource.Failure("Failed to fetch movie list ${e.localizedMessage}")
//        }
//    }

    suspend fun movieDetails(movieId : Int) : Resource<MovieDetailResponse> {
        return try {
            val response = movieApi.movieDetails(movieId)
            if (response.isSuccessful) {
                val movieDetailResponse = response.body()
                val movieDetail = movieDetailResponse!!
                Resource.Success(movieDetail)
            } else {
                Resource.Failure("Failed to fetch movie details")
            }
        } catch (e : Exception) {
            Resource.Failure("Failed to fetch movie details ${e.localizedMessage}")
        }
    }

    suspend fun movieCredits(movieId : Int) : Resource<MovieTvShowCreditsResponse> {
        return try {
            val response = movieApi.movieCredits(movieId)
            if (response.isSuccessful) {
                val movieCreditsResponse = response.body()
                val movieCredit = movieCreditsResponse!!
                Resource.Success(movieCredit)
            } else {
                Resource.Failure("Failed to fetch movie credits")
            }
        } catch (e : Exception) {
            Resource.Failure("Failed to fetch movie credits ${e.localizedMessage}")
        }
    }
}