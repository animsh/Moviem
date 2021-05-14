package com.animsh.moviem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.ItemCastContainerBinding
import com.animsh.moviem.models.movie.Cast
import com.animsh.moviem.util.MovieDiffUtil

/**
 * Created by animsh on 5/14/2021.
 */
class CastAdapter : RecyclerView.Adapter<CastAdapter.CrewViewHolder>() {

    private var castList = emptyList<Cast>()

    class CrewViewHolder(private val binding: ItemCastContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(cast: Cast) {
            binding.cast = cast
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CrewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCastContainerBinding.inflate(layoutInflater, parent, false)
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
        val current = castList[position]
        holder.bindData(current)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    fun setData(newData: List<Cast>) {
        val diffUtil = MovieDiffUtil(castList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        castList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        castList = emptyList()
    }
}