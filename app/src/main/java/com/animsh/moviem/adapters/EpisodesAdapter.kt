package com.animsh.moviem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.LayoutEpisodeContainerBinding
import com.animsh.moviem.models.tv.episodes.Episode
import com.animsh.moviem.util.TvDiffUtil

/**
 * Created by animsh on 5/15/2021.
 */
class EpisodesAdapter : RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>() {

    private var epList = emptyList<Episode>()

    class EpisodeViewHolder(private val binding: LayoutEpisodeContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(episode: Episode) {
            binding.episode = episode
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EpisodeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutEpisodeContainerBinding.inflate(layoutInflater, parent, false)
                return EpisodeViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeViewHolder {
        return EpisodeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val current = epList[position]
        holder.bindData(current)
    }

    override fun getItemCount(): Int {
        return epList.size
    }

    fun setData(newData: List<Episode>) {
        val diffUtil = TvDiffUtil(epList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        epList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        epList = emptyList()
    }
}