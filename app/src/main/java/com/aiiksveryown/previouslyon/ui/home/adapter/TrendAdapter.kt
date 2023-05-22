package com.aiiksveryown.previouslyon.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.ItemTrendBinding
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.daily_trending.DailyTrending
import com.aiiksveryown.previouslyon.util.Constants

class TrendAdapter : RecyclerView.Adapter<TrendAdapter.TrendViewHolder>() {

    private var dailyTrending = listOf<DailyTrending>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        val binding = ItemTrendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        holder.bind(dailyTrending[position])
    }

    override fun getItemCount(): Int = dailyTrending.size

    fun updateList(dailyTrending: List<DailyTrending>) {
        this.dailyTrending = dailyTrending
        notifyDataSetChanged()
    }


    class TrendViewHolder(private val binding: ItemTrendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dailyTrending: DailyTrending) {

            with(binding) {
                val imagePath = Constants.TMDB_IMAGE_ORIGINAL + dailyTrending.posterPath
                imagePath.loadImageURL(imageTrend)
            }
        }
    }
}

fun String.loadImageURL(imageView: ImageView) {
    imageView.load(this) {
        placeholder(R.drawable.loading)
        error(R.drawable.ic_profile_place_holder)
    }
}

