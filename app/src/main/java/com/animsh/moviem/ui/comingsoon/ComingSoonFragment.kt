package com.animsh.moviem.ui.comingsoon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.animsh.moviem.adapters.ComingSoonAdapter
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.databinding.FragmentComingSoonBinding
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonFragment : Fragment() {

    private var _binding: FragmentComingSoonBinding? = null
    private val binding get() = _binding

    private lateinit var moviesViewModel: MoviesViewModel
    private val comingSoonAdapter by lazy { ComingSoonAdapter(childFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentComingSoonBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this!!.comingSoonMoviesRv.adapter = comingSoonAdapter
            comingSoonMoviesRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            requestApiData()
        }
    }

    private fun requestApiData() {
        moviesViewModel.getComingSonMovies(Constants.API_KEY, 1)
        moviesViewModel.comingSoonResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding!!.comingSoonMoviesRv.hideShimmer()
                    response.data?.let {
                        comingSoonAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    binding!!.comingSoonMoviesRv.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding!!.comingSoonMoviesRv.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}