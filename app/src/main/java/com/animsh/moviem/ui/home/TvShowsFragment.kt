package com.animsh.moviem.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.animsh.moviem.adapters.TvAdapter
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.FragmentTvShowsBinding
import com.animsh.moviem.util.Constants.Companion.API_KEY
import com.animsh.moviem.util.NetworkResult
import kotlinx.android.synthetic.main.fragment_tv_shows.view.*

class TvShowsFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var fragmentTvShowsBinding: FragmentTvShowsBinding
    private val trendingTvAdapter by lazy { TvAdapter() }
    private val onAirTvAdapter by lazy { TvAdapter() }
    private val airingTodayTvAdapter by lazy { TvAdapter() }
    private val popularTvAdapter by lazy { TvAdapter() }
    private val topTvAdapter by lazy { TvAdapter() }
    private lateinit var tvViewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        mView = fragmentTvShowsBinding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvViewModel = ViewModelProvider(requireActivity()).get(TvViewModel::class.java)
        setupRecyclerView()
        requestApiData()

        fragmentTvShowsBinding.refreshLayout.setOnRefreshListener {
            showShimmer()
            trendingTvAdapter.clearList()
            popularTvAdapter.clearList()
            topTvAdapter.clearList()
            onAirTvAdapter.clearList()
            airingTodayTvAdapter.clearList()
            requestApiData()
            fragmentTvShowsBinding.refreshLayout.isRefreshing = false
        }
    }

    private fun requestApiData() {
        tvViewModel.getLatestTv(API_KEY)
        tvViewModel.latestTvResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.posterShimmer.hideShimmer()
                    mView.posterShimmer.visibility = View.GONE
                    response.data?.let {
                        fragmentTvShowsBinding.latestTv = it
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.posterShimmer.hideShimmer()
                    mView.posterShimmer.visibility = View.GONE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.posterShimmer.visibility = View.VISIBLE
                    mView.posterShimmer.showShimmer(true)
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getTrendingTv(API_KEY, 1)
        tvViewModel.trendingResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.trendingTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        trendingTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.trendingTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.trendingTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getPopularTv(API_KEY, 1)
        tvViewModel.popularTvOResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.popularTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        popularTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.popularTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.popularTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getTopTv(API_KEY, 1)
        tvViewModel.topTvResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.topTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        topTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.topTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.topTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getAiringToday(API_KEY, 1)
        tvViewModel.tvAiringTodayResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.airingTodayTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        airingTodayTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.airingTodayTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.airingTodayTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getOnAirTv(API_KEY, 1)
        tvViewModel.tvOnAirResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.onAirTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        onAirTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.onAirTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.onAirTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        mView.trendingTvShowRecyclerview.adapter = trendingTvAdapter
        mView.trendingTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.topTvShowRecyclerview.adapter = topTvAdapter
        mView.topTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.popularTvShowRecyclerview.adapter = popularTvAdapter
        mView.popularTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.onAirTvShowRecyclerview.adapter = onAirTvAdapter
        mView.onAirTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.airingTodayTvShowRecyclerview.adapter = airingTodayTvAdapter
        mView.airingTodayTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        showShimmer()
    }

    fun showShimmer() {
        mView.posterShimmer.showShimmer(true)
        mView.trendingTvShowRecyclerview.showShimmer()
        mView.airingTodayTvShowRecyclerview.showShimmer()
        mView.onAirTvShowRecyclerview.showShimmer()
        mView.popularTvShowRecyclerview.showShimmer()
        mView.topTvShowRecyclerview.showShimmer()
    }
}