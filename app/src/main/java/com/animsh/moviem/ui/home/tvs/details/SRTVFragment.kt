package com.animsh.moviem.ui.home.tvs.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.animsh.moviem.adapters.TvAdapter
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.FragmentSrTvBinding
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SRTVFragment(
    private val tveId: Int,
    private val choice: Int
) : Fragment() {

    private var _binding: FragmentSrTvBinding? = null
    private val binding get() = _binding

    private lateinit var tvViewModel: TvViewModel
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSrTvBinding.inflate(layoutInflater, container, false)
        tvViewModel = ViewModelProvider(requireActivity()).get(TvViewModel::class.java)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvAdapter = TvAdapter(childFragmentManager)
            this!!.tvRecycleView.adapter = tvAdapter
            tvRecycleView.layoutManager =
                GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            if (choice == 0) {
                requestSimilarTV()
            } else if (choice == 1) {
                requestRecommendationTV()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestSimilarTV() {
        tvViewModel.getSimilarTv(tveId, Constants.API_KEY, 1)
        tvViewModel.similarTvResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding!!.tvRecycleView.hideShimmer()
                    response.data?.let {
                        tvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    binding!!.tvRecycleView.hideShimmer()
                    binding!!.warning.visibility = View.VISIBLE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding!!.tvRecycleView.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }

    private fun requestRecommendationTV() {
        tvViewModel.getRecommendedTv(tveId, Constants.API_KEY, 1)
        tvViewModel.recommendedTvResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding!!.tvRecycleView.hideShimmer()
                    response.data?.let {
                        tvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    binding!!.tvRecycleView.hideShimmer()
                    binding!!.warning.visibility = View.VISIBLE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding!!.tvRecycleView.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }


}