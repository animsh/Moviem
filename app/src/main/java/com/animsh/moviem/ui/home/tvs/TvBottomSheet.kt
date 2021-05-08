package com.animsh.moviem.ui.home.tvs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animsh.moviem.databinding.LayoutBottomSheetTvBinding
import com.animsh.moviem.models.tv.Result
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
        binding.apply {
            data = result
        }
    }

    fun newInstance(): TvBottomSheet {
        return TvBottomSheet(result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}