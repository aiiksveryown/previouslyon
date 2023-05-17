package com.aiiksveryown.previouslyon.feature.tv_show.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.LayoutTvShowBinding
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.search.SearchTvShow
import com.aiiksveryown.previouslyon.feature.tv_show.data.database.entity.TvShowEntity
import com.aiiksveryown.previouslyon.feature.tv_show.presentation.TvShowFragmentDirections
import com.aiiksveryown.previouslyon.feature.tv_show.presentation.TvShowSearchFragmentDirections
import com.aiiksveryown.previouslyon.util.Constants.TMDB_IMAGE_ORIGINAL
import com.aiiksveryown.previouslyon.util.utils.DateUtils
import com.aiiksveryown.previouslyon.util.utils.DiffUtils
import com.aiiksveryown.previouslyon.util.utils.formatVoteAverage
import com.aiiksveryown.previouslyon.util.utils.toSearchTv
import com.aiiksveryown.previouslyon.util.utils.toTvShowEntity

class TvShowAdapter(
    private val isFromApi : Boolean
) : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private var tvShowResult = listOf<SearchTvShow>()
    private var tvShowResultEntity = listOf<TvShowEntity>()

    fun submitTvShowEntity(newTvShow : List<TvShowEntity>) {
        val oldTvShow = DiffUtils(newTvShow, tvShowResultEntity)
        val result = DiffUtil.calculateDiff(oldTvShow)
        tvShowResultEntity = newTvShow
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    fun submitTvShow(newTvShow : List<SearchTvShow>) {
        val oldTvShow = DiffUtils(newTvShow, tvShowResult)
        val result = DiffUtil.calculateDiff(oldTvShow)
        tvShowResult = newTvShow
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    class TvShowViewHolder(private val binding : LayoutTvShowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShowEntity: TvShowEntity) {

            with(binding) {

                txtTvShowTitle.text = tvShowEntity.tvShowName
                txtDescription.text = tvShowEntity.tvShowOverview
                txtVoteAverage.text = tvShowEntity.tvShowVoteAverage
                if (tvShowEntity.tvShowPosterPath.isNotEmpty()) {
                    imgTvShow.load(TMDB_IMAGE_ORIGINAL + tvShowEntity.tvShowPosterPath) {
                        crossfade(true)
                        crossfade(500)
                    }
                }
                if (tvShowEntity.tvShowFirstAirDate != null && !tvShowEntity.tvShowFirstAirDate.isNullOrEmpty()) {
                    txtReleaseDate.text = DateUtils.formatAirDate(tvShowEntity.tvShowFirstAirDate)
                } else {
                    txtReleaseDate.visibility = View.GONE
                }

                layoutTvShow.setOnClickListener {
                    val action = TvShowFragmentDirections.actionTvShowFragmentToTvShowDetailFragment(
                        tvShow = tvShowEntity.toSearchTv(),
                        tvShowEntity = tvShowEntity
                    )
                    it.findNavController().navigate(action)
                }
            }
        }

        fun bind(tvShowResult : SearchTvShow) {
            with(binding) {

                if (tvShowResult.poster_path != null) {
                    imgTvShow.load(TMDB_IMAGE_ORIGINAL + tvShowResult.poster_path) {
                        crossfade(true)
                        crossfade(500)
                    }
                } else {
                    imgTvShow.setImageResource(R.drawable.ic_movie_error)
                }

                if (tvShowResult.first_air_date != null && !tvShowResult.first_air_date.isNullOrEmpty()) {
                    txtReleaseDate.text = DateUtils.formatAirDate(tvShowResult.first_air_date)
                } else {
                    txtReleaseDate.visibility = View.GONE
                }

                txtVoteAverage.text = tvShowResult.vote_average.formatVoteAverage()
                txtTvShowTitle.text = tvShowResult.name
                txtDescription.text = tvShowResult.overview

                layoutTvShow.setOnClickListener {
                    val action = TvShowSearchFragmentDirections.actionTvShowSearchFragmentToTvShowDetailFragment(
                        tvShow = tvShowResult,
                        tvShowEntity = tvShowResult.toTvShowEntity()
                    )
                    it.findNavController().navigate(action)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowViewHolder {
        val binding = LayoutTvShowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        if (isFromApi) {
            holder.bind(tvShowResult[position])
        } else {
            holder.bind(tvShowResultEntity[position])
        }
    }

    override fun getItemCount(): Int {
        return if (isFromApi) {
            tvShowResult.size
        } else {
            tvShowResultEntity.size
        }
    }
}