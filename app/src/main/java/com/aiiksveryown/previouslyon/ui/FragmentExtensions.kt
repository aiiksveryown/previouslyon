package com.aiiksveryown.previouslyon.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setTitle(visibility: Boolean, title: String? = null) {
    if (visibility) {
        (activity as AppCompatActivity).supportActionBar?.show()
        title?.let {
            (activity as AppCompatActivity).supportActionBar?.title = it
        }
    } else {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}