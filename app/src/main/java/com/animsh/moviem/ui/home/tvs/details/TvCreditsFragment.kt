package com.animsh.moviem.ui.home.tvs.details

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
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.FragmentTvCreditsBinding
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvCreditsFragment(
    private val tvId: Int
) : Fragment() {

    private var _binding: FragmentTvCreditsBinding? = null
    private val binding get() = _binding

    private lateinit var tvViewModel: TvViewModel
    private val crewAdapter: CrewAdapter by lazy { CrewAdapter() }
    private val castAdapter: CastAdapter by lazy { CastAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvViewModel = ViewModelProvider(requireActivity()).get(TvViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvCreditsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
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

            requestCreditsTv()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestCreditsTv() {
        tvViewModel.getCreditsTv(tvId, Constants.API_KEY)
        tvViewModel.creditResponse.observe(viewLifecycleOwner, { response ->
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