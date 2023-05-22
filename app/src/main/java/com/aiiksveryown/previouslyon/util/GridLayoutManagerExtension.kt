package com.aiiksveryown.previouslyon.util

import androidx.recyclerview.widget.GridLayoutManager
import com.aiiksveryown.previouslyon.ui.shows_lists.adapter.LoadUIStateAdapter
import com.aiiksveryown.previouslyon.ui.shows_lists.adapter.ShowsListAdapter

fun <T : Any> GridLayoutManager.setSpanSize(
    footerAdapter: LoadUIStateAdapter, adapter: ShowsListAdapter, spanCount: Int
) {
    this.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if ((position == adapter.itemCount) && footerAdapter.itemCount > 0) {
                spanCount
            } else {
                1
            }
        }
    }
}