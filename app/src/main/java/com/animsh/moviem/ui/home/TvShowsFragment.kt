package com.animsh.moviem.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.animsh.moviem.R
import kotlinx.android.synthetic.main.fragment_tv_shows.view.*

class TvShowsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tv_shows, container, false)

        view.posterShimmer.showShimmer(true)
        view.genreRecyclerview.showShimmer()
        view.trendingTvShowRecyclerview.showShimmer()
        view.airingTodayTvShowRecyclerview.showShimmer()
        view.onAirTvShowRecyclerview.showShimmer()
        view.popularTvShowRecyclerview.showShimmer()
        view.topTvShowRecyclerview.showShimmer()

        return view
    }
}