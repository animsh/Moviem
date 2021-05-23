package com.animsh.moviem.adapters

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.R
import com.animsh.moviem.data.database.entity.FavoriteTVEntity
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.LayoutFavTvItemContainerBinding
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.ui.home.tvs.details.TVDetailsActivity
import com.animsh.moviem.util.TvDiffUtil
import com.google.android.material.snackbar.Snackbar

/**
 * Created by animsh on 2/27/2021.
 */
class FavTvAdapter(
    private var activity: Activity,
    private val tvViewModel: TvViewModel
) : RecyclerView.Adapter<FavTvAdapter.TvViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private lateinit var dActionMode: ActionMode
    private var selectedTvs = arrayListOf<FavoriteTVEntity>()
    private var myHolders = arrayListOf<TvViewHolder>()
    private lateinit var rootView: View

    private var tvsList = emptyList<FavoriteTVEntity>()

    class TvViewHolder(val binding: LayoutFavTvItemContainerBinding) :
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
        rootView = holder.itemView.rootView
        myHolders.add(holder)
        val currentTv = tvsList[position]
        holder.bindData(currentTv.result)
        saveSelection(holder, currentTv)
        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentTv)
            } else {
                val context = holder.itemView.context
                val intent = Intent(context, TVDetailsActivity::class.java)
                intent.putExtra("tv", currentTv.result)
                context?.startActivity(intent)
            }
        }
        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                activity.startActionMode(this)
                applySelection(holder, currentTv)
                true
            } else {
                applySelection(holder, currentTv)
                true
            }

        }
    }

    override fun getItemCount(): Int {
        return tvsList.size
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.fav_contextual_menu, menu)
        dActionMode = actionMode!!
        when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                applyStatusColor(R.color.backgroundColorDark)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                applyStatusColor(R.color.backgroundColorLight)
            }
        }
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_fav_recipe) {
            selectedTvs.forEach {
                tvViewModel.deleteFavTV(it)
            }
            showSnackBarMessage("${selectedTvs.size} tv show/s deleted!!")
            selectedTvs.clear()
            multiSelection = false
            dActionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myHolders.forEach { holder ->
            setColorAsPerMode(holder)
        }
        multiSelection = false
        selectedTvs.clear()
    }

    fun setData(newData: List<FavoriteTVEntity>) {
        val tvDiffUtil = TvDiffUtil(tvsList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(tvDiffUtil)
        tvsList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        tvsList = emptyList()
    }

    private fun saveSelection(holder: TvViewHolder, current: FavoriteTVEntity) {
        if (selectedTvs.contains(current)) {
            changeStyle(holder, R.color.redColorLight, R.color.redColor)
        } else {
            setColorAsPerMode(holder)
        }
    }

    private fun applySelection(holder: TvViewHolder, current: FavoriteTVEntity) {
        if (selectedTvs.contains(current)) {
            selectedTvs.remove(current)
            setColorAsPerMode(holder)
            applyActionModeTitle()
        } else {
            selectedTvs.add(current)
            changeStyle(holder, R.color.redColorLight, R.color.redColor)
            applyActionModeTitle()
        }
    }

    private fun applyActionModeTitle() {
        when (selectedTvs.size) {
            0 -> dActionMode.finish()
            1 -> dActionMode.title = "${selectedTvs.size} item selected"
            else -> dActionMode.title = "${selectedTvs.size} items selected"
        }
    }

    private fun changeStyle(
        holder: TvViewHolder,
        backGroundColor: Int,
        strokeColor: Int
    ) {
        holder.binding.backCard.setBackgroundColor(
            ContextCompat.getColor(
                activity,
                backGroundColor
            )
        )

        holder.binding.backCard.strokeWidth = 1
        holder.binding.backCard.strokeColor = ContextCompat.getColor(
            activity,
            strokeColor
        )
    }

    private fun applyStatusColor(color: Int) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, color)
    }


    private fun setColorAsPerMode(holder: TvViewHolder) {
        when (activity.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                changeStyle(
                    holder,
                    R.color.backgroundLayoutColorDark,
                    R.color.backgroundLayoutColorDark
                )
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                changeStyle(
                    holder,
                    R.color.backgroundLayoutColorLight,
                    R.color.backgroundLayoutColorLight
                )
            }
        }
    }


    private fun showSnackBarMessage(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    fun removeContextualActionMode() {
        if (this::dActionMode.isInitialized) {
            dActionMode.finish()
        }
    }

}