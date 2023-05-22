package com.aiiksveryown.previouslyon.feature.tv_show.data.api

interface Mapper<I, O> {
    fun map(input: I): O
}