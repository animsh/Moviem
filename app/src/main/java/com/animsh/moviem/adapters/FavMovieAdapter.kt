package com.animsh.moviem.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.LayoutFavMovieItemContainerBinding
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.ui.home.movies.details.MovieDetailsActivity
import com.animsh.moviem.util.MovieDiffUtil


/**
 * Created by animsh on 2/27/2021.
 */
class FavMovieAdapter : RecyclerView.Adapter<FavMovieAdapter.MovieViewHolder>() {

    private var moviesList = emptyList<Movie>()

    class MovieViewHolder(private val binding: LayoutFavMovieItemContainerBinding) :
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
        val currentMovie = moviesList[position]
        holder.bindData(currentMovie)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra("movie", currentMovie)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun setListData(newData: List<Movie>) {
        val movieDiffUtil = MovieDiffUtil(moviesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        moviesList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        moviesList = emptyList()
    }
}