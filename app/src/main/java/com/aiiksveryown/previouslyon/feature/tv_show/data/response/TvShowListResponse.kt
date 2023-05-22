package com.aiiksveryown.previouslyon.feature.tv_show.data.response

import com.google.gson.annotations.SerializedName

data class TvShowListResponse<T>(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val items: List<T>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
)