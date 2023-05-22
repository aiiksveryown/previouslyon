package com.aiiksveryown.previouslyon.ui.shows_lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aiiksveryown.previouslyon.databinding.ItemCategoryBinding
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.Media
import com.aiiksveryown.previouslyon.ui.home.adapter.loadImageURL
import com.aiiksveryown.previouslyon.util.Constants

class ShowsListAdapter(
    diffCallback: DiffUtil.ItemCallback<Media>
    ) : PagingDataAdapter<Media, ShowsListAdapter.ShowListViewHolder>(diffCallback){
    override fun onBindViewHolder(holder: ShowListViewHolder, position: Int) {
       holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowListViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShowListViewHolder(binding)
    }

    class ShowListViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(media: Media) {
            with(binding) {
                val imagePath = Constants.TMDB_IMAGE_ORIGINAL + media.mediaImage
                imagePath.loadImageURL(mediaPoster)
            }
        }
    }
}