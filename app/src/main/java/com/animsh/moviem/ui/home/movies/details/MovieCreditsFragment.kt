package com.animsh.moviem.ui.home.movies.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.animsh.moviem.adapters.CastAdapter
import com.animsh.moviem.adapters.CrewAdapter
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.databinding.FragmentCreditsBinding
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class MovieCreditsFragment(
    private val movieId: Int
) : Fragment() {

    private var _binding: FragmentCreditsBinding? = null
    private val binding get() = _binding

    private lateinit var moviesViewModel: MoviesViewModel
    private val crewAdapter: CrewAdapter by lazy { CrewAdapter() }
    private val castAdapter: CastAdapter by lazy { CastAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreditsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this!!.crewRv.adapter = crewAdapter
            castRv.adapter = castAdapter
            crewRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            castRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            requestCreditsMovies()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestCreditsMovies() {
        moviesViewModel.getMovieCredits(movieId, Constants.API_KEY)
        moviesViewModel.movieCreditsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding!!.crewRv.hideShimmer()
                    response.data?.let {
                        crewAdapter.setData(it.crew)
                        castAdapter.setData(it.cast)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    binding!!.crewRv.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding!!.crewRv.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }
}