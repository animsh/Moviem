package com.animsh.moviem.ui.home.movies.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.animsh.moviem.adapters.MovieAdapter
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.databinding.FragmentSrMoviesBinding
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SRMoviesFragment(
    private val movieId: Int,
    private val choice: Int
) : Fragment() {

    private var _binding: FragmentSrMoviesBinding? = null
    private val binding get() = _binding

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSrMoviesBinding.inflate(layoutInflater, container, false)
        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            movieAdapter = MovieAdapter(childFragmentManager)
            this!!.moviesRecycleView.adapter = movieAdapter
            moviesRecycleView.layoutManager =
                GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            if (choice == 0) {
                requestSimilarMovies()
            } else if (choice == 1) {
                requestRecommendationMovies()
            }
        }
    }

    private fun requestRecommendationMovies() {
        moviesViewModel.getRecommendationsMovies(movieId, Constants.API_KEY, 1)
        moviesViewModel.recommendationMoviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding!!.moviesRecycleView.hideShimmer()
                    response.data?.let {
                        movieAdapter.setCommonData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    binding!!.moviesRecycleView.hideShimmer()
                    binding!!.warning.visibility = View.VISIBLE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding!!.moviesRecycleView.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestSimilarMovies() {
        moviesViewModel.getSimilarMovies(movieId, Constants.API_KEY, 1)
        moviesViewModel.similarMoviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding!!.moviesRecycleView.hideShimmer()
                    response.data?.let {
                        movieAdapter.setCommonData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    binding!!.moviesRecycleView.hideShimmer()
                    binding!!.warning.visibility = View.VISIBLE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding!!.moviesRecycleView.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }
}