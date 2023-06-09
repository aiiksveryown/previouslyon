package com.aiiksveryown.previouslyon.feature.tv_show.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aiiksveryown.previouslyon.R
import com.aiiksveryown.previouslyon.databinding.LayoutCastBinding
import com.aiiksveryown.previouslyon.feature.tv_show.data.api.detail.Cast
import com.aiiksveryown.previouslyon.util.Constants.TMDB_IMAGE_ORIGINAL
import com.aiiksveryown.previouslyon.util.utils.DiffUtils

class CastAdapter : RecyclerView.Adapter<CastAdapter.CreditViewHolder>() {

    private var castResult = listOf<Cast>()

    fun submitCast(newCast : List<Cast>) {
        val oldCast = DiffUtils(newCast, castResult)
        val result = DiffUtil.calculateDiff(oldCast)
        castResult = newCast
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditViewHolder {
        val binding = LayoutCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CreditViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        holder.bind(castResult[position])
    }

    override fun getItemCount(): Int {
        return castResult.size
    }

    class CreditViewHolder(private val binding : LayoutCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(castResult : Cast) {
            with(binding) {
                txtActorName.text = castResult.name
                if (castResult.profile_path != null) {
                    imgCast.load(TMDB_IMAGE_ORIGINAL + castResult.profile_path) {
                        crossfade(true)
                        crossfade(500)
                    }
                } else {
                    imgCast.setImageResource(R.drawable.ic_person)
                }
            }
        }
    }
}