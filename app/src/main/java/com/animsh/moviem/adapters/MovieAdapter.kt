package com.animsh.moviem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.LayoutMovieItemContainerBinding
import com.animsh.moviem.models.movie.CommonMovieResponse
import com.animsh.moviem.models.movie.Result
import com.animsh.moviem.models.movie.UniqueMovieResponse
import com.animsh.moviem.ui.home.movies.MoviesBottomSheet
import com.animsh.moviem.util.MovieDiffUtil


/**
 * Created by animsh on 2/27/2021.
 */
class MovieAdapter(
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var moviesList = emptyList<Result>()

    class MovieViewHolder(private val binding: LayoutMovieItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: Result) {
            binding.movie = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutMovieItemContainerBinding.inflate(layoutInflater, parent, false)
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
            val openBottomSheet: MoviesBottomSheet =
                MoviesBottomSheet(currentMovie).newInstance()
            openBottomSheet.show(
                fragmentManager,
                MoviesBottomSheet.TAG
            )
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun setCommonData(newData: CommonMovieResponse) {
        val movieDiffUtil = MovieDiffUtil(moviesList, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        moviesList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun setUniqueData(newData: UniqueMovieResponse) {
        val movieDiffUtil = MovieDiffUtil(moviesList, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        moviesList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        moviesList = emptyList()
    }
}