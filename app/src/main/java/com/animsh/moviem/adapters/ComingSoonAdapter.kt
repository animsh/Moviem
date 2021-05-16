package com.animsh.moviem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.animsh.moviem.databinding.LayoutComingSoonMovieContainerBinding
import com.animsh.moviem.models.movie.ComingSoonResponse
import com.animsh.moviem.models.movie.Result
import com.animsh.moviem.ui.home.movies.MoviesBottomSheet
import com.animsh.moviem.util.MovieDiffUtil


/**
 * Created by animsh on 2/27/2021.
 */
class ComingSoonAdapter(
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ComingSoonAdapter.MovieViewHolder>() {

    private var moviesList = emptyList<Result>()

    class MovieViewHolder(private val binding: LayoutComingSoonMovieContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: Result) {
            binding.data = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    LayoutComingSoonMovieContainerBinding.inflate(layoutInflater, parent, false)
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

    fun setData(newData: ComingSoonResponse) {
        val movieDiffUtil = MovieDiffUtil(moviesList, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(movieDiffUtil)
        moviesList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearList() {
        moviesList = emptyList()
    }
}