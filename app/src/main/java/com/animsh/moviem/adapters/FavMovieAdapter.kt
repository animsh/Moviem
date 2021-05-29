package com.animsh.moviem.adapters

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.R
import com.animsh.moviem.data.database.entity.FavoriteMovieEntity
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.databinding.LayoutFavMovieItemContainerBinding
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.ui.home.movies.details.MovieDetailsActivity
import com.animsh.moviem.util.MovieDiffUtil
import com.google.android.material.snackbar.Snackbar


/**
 * Created by animsh on 2/27/2021.
 */
class FavMovieAdapter(
    private var activity: Activity,
    private val moviesViewModel: MoviesViewModel,
    private val isHome: Boolean
) : RecyclerView.Adapter<FavMovieAdapter.MovieViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private lateinit var dActionMode: ActionMode
    private var selectedMovies = arrayListOf<FavoriteMovieEntity>()
    private var myHolders = arrayListOf<MovieViewHolder>()
    private lateinit var rootView: View

    private var moviesList = emptyList<FavoriteMovieEntity>()

    class MovieViewHolder(val binding: LayoutFavMovieItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: Movie) {
            binding.movie = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    LayoutFavMovieItemContainerBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        rootView = holder.itemView.rootView
        myHolders.add(holder)
        val currentMovie = moviesList[position]
        holder.bindData(currentMovie.result)
        saveSelection(holder, currentMovie)

        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentMovie)
            } else {
                val context = holder.itemView.context
                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra("movie", currentMovie.result)
                context?.startActivity(intent)
            }
        }

        if (!isHome) {
            holder.itemView.setOnLongClickListener {
                if (!multiSelection) {
                    multiSelection = true
                    activity.startActionMode(this)
                    applySelection(holder, currentMovie)
                    true
                } else {
                    applySelection(holder, currentMovie)
                    true
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return moviesList.size
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
            selectedMovies.forEach {
                moviesViewModel.deleteFavMovie(it)
            }
            showSnackBarMessage("${selectedMovies.size} movie/s deleted!!")
            selectedMovies.clear()
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
        selectedMovies.clear()
    }

    fun setListData(newData: List<FavoriteMovieEntity>) {
        val movieDiffUtil = MovieDiffUtil(moviesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        moviesList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        moviesList = emptyList()
    }

    private fun applySelection(
        holder: MovieViewHolder,
        current: FavoriteMovieEntity
    ) {
        if (selectedMovies.contains(current)) {
            selectedMovies.remove(current)
            setColorAsPerMode(holder)
            applyActionModeTitle()
        } else {
            selectedMovies.add(current)
            changeStyle(holder, R.color.redColorLight, R.color.redColor)
            applyActionModeTitle()
        }
    }

    private fun applyActionModeTitle() {
        when (selectedMovies.size) {
            0 -> dActionMode.finish()
            1 -> dActionMode.title = "${selectedMovies.size} item selected"
            else -> dActionMode.title = "${selectedMovies.size} items selected"
        }
    }

    private fun saveSelection(
        holder: MovieViewHolder,
        currentMovie: FavoriteMovieEntity
    ) {
        if (selectedMovies.contains(currentMovie)) {
            changeStyle(holder, R.color.redColorLight, R.color.redColor)
        } else {
            setColorAsPerMode(holder)
        }
    }

    private fun changeStyle(
        holder: MovieViewHolder,
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

    private fun setColorAsPerMode(holder: MovieViewHolder) {
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