package com.animsh.moviem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.ItemCrewContainerBinding
import com.animsh.moviem.models.movie.Crew
import com.animsh.moviem.util.MovieDiffUtil

/**
 * Created by animsh on 5/14/2021.
 */
class CrewAdapter : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

    private var crewList = emptyList<Crew>()

    class CrewViewHolder(private val binding: ItemCrewContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(crew: Crew) {
            binding.crew = crew
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CrewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCrewContainerBinding.inflate(layoutInflater, parent, false)
                return CrewViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewViewHolder {
        return CrewViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        val current = crewList[position]
        holder.bindData(current)
    }

    override fun getItemCount(): Int {
        return crewList.size
    }

    fun setData(newData: List<Crew>) {
        val diffUtil = MovieDiffUtil(crewList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        crewList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        crewList = emptyList()
    }
}