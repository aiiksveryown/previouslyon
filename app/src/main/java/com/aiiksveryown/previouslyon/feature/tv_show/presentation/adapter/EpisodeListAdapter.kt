package com.aiiksveryown.previouslyon.feature.tv_show.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.LayoutEpisodeListBinding
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.season.Episode
import com.aiiksveryown.previouslyon.util.Constants.TMDB_IMAGE_ORIGINAL
import com.aiiksveryown.previouslyon.util.utils.DateUtils
import com.aiiksveryown.previouslyon.util.utils.DiffUtils

class EpisodeListAdapter : RecyclerView.Adapter<EpisodeListAdapter.EpisodeViewHolder>() {

    private var episodeResult = listOf<Episode>()

    fun submitEpisode(newEpisode: List<Episode>) {
        val oldEpisode = DiffUtils(newEpisode, episodeResult)
        val result = DiffUtil.calculateDiff(oldEpisode)
        episodeResult = newEpisode
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    class EpisodeViewHolder(private val binding: LayoutEpisodeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(episodeResult: Episode) {

            with(binding) {

                txtName.text = episodeResult.name
                txtEpisodeNumber.text =
                    root.context.getString(R.string.episode) + " ${episodeResult.episode_number}"

                if (episodeResult.air_date != null && !episodeResult.air_date.isNullOrEmpty()) {
                    txtInfo.text = DateUtils.formatAirDate(episodeResult.air_date) + " • " + episodeResult.runtime + "min"
                } else {
                    txtInfo.text = "${episodeResult.runtime}min"
                }


                if (episodeResult.still_path != null) {
                    imgEpisodePoster.load(TMDB_IMAGE_ORIGINAL + episodeResult.still_path) {
                        crossfade(true)
                        crossfade(500)
                    }
                } else {
                    imgEpisodePoster.setImageResource(R.drawable.ic_movie_error)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeViewHolder {
        val binding = LayoutEpisodeListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodeResult[position])
    }

    override fun getItemCount(): Int {
        return episodeResult.size
    }
}