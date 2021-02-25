package com.animsh.moviem.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.animsh.moviem.R
import kotlinx.android.synthetic.main.fragment_movies.view.*

class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        view.genreRecyclerview.showShimmer()
        view.trendingMoviesShowRecyclerview.showShimmer()
        view.nowPlayingMoviesShowRecyclerview.showShimmer()
        view.popularMoviesShowRecyclerview.showShimmer()
        view.topMoviesShowRecyclerview.showShimmer()

        return view
    }
}