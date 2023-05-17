package com.aiiksveryown.previouslyon.feature.tv_show.data.api.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String
) : Parcelable