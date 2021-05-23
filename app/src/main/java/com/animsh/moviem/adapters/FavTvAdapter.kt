package com.animsh.moviem.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.LayoutFavTvItemContainerBinding
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.ui.home.tvs.details.TVDetailsActivity
import com.animsh.moviem.util.TvDiffUtil

/**
 * Created by animsh on 2/27/2021.
 */
class FavTvAdapter : RecyclerView.Adapter<FavTvAdapter.TvViewHolder>() {

    private var tvsList = emptyList<TV>()

    class TvViewHolder(private val binding: LayoutFavTvItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: TV) {
            binding.tv = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TvViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutFavTvItemContainerBinding.inflate(layoutInflater, parent, false)
                return TvViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvViewHolder {
        return TvViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val currentTv = tvsList[position]
        holder.bindData(currentTv)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, TVDetailsActivity::class.java)
            intent.putExtra("tv", currentTv)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return tvsList.size
    }

    fun setData(newData: List<TV>) {
        val tvDiffUtil = TvDiffUtil(tvsList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(tvDiffUtil)
        tvsList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        tvsList = emptyList()
    }
}