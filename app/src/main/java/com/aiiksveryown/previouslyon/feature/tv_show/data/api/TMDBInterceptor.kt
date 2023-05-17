package com.aiiksveryown.previouslyon.feature.tv_show.data.api

import com.aiiksveryown.previouslyon.BuildConfig.TMDB_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class TMDBInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", TMDB_API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}