package com.aiiksveryown.previouslyon.di

import com.aiiksveryown.previouslyon.feature.tv_show.data.api.TvShowApi
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.TMDBInterceptor
import com.aiiksveryown.previouslyon.util.Constants.TMDB_BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        TMDBInterceptor: TMDBInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(TMDBInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideTvShowInstance(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) : TvShowApi {
        return Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
            .create(TvShowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieInterceptor() : TMDBInterceptor {
        return TMDBInterceptor()
    }

}