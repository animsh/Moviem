package com.animsh.moviem.ui.home.tvs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.LayoutBottomSheetTvBinding
import com.animsh.moviem.models.tv.Result
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by animsh on 5/8/2021.
 */
class TvBottomSheet(
    private val result: Result
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "TVBOTTOMSHEET"
    }

    private var _binding: LayoutBottomSheetTvBinding? = null
    private val binding get() = _binding!!
    private lateinit var tvViewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutBottomSheetTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvViewModel = ViewModelProvider(requireActivity()).get(TvViewModel::class.java)
        binding.apply {
            requestApiData(result.id)
        }
    }

    fun newInstance(): TvBottomSheet {
        return TvBottomSheet(result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestApiData(tvId: Int) {
        tvViewModel.getTvDetails(tvId, Constants.API_KEY).invokeOnCompletion {
            Log.d(
                "TAGTAGTAG",
                "requestApiData: " + tvViewModel.tvDetailsResponse.value?.data?.id
            )
        }
        tvViewModel.tvDetailsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        binding.data = it
                        Log.d("LOGDATA", "requestApiData: $it")
                    }
                }
                is NetworkResult.Error -> {
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }

}