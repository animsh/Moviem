package com.animsh.moviem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.LayoutTvItemContainerBinding
import com.animsh.moviem.models.tv.Result
import com.animsh.moviem.models.tv.TvResponse
import com.animsh.moviem.util.TvDiffUtil

/**
 * Created by animsh on 2/27/2021.
 */
class TvAdapter : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    private var tvsList = emptyList<Result>()

    class TvViewHolder(private val binding: LayoutTvItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: Result) {
            binding.tv = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TvViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutTvItemContainerBinding.inflate(layoutInflater, parent, false)
                return TvViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvAdapter.TvViewHolder {
        return TvViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TvAdapter.TvViewHolder, position: Int) {
        val currentTv = tvsList[position]
        holder.bindData(currentTv)
    }

    override fun getItemCount(): Int {
        return tvsList.size
    }

    fun setData(newData: TvResponse) {
        val tvDiffUtil = TvDiffUtil(tvsList, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(tvDiffUtil)
        tvsList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        tvsList = emptyList()
    }
}