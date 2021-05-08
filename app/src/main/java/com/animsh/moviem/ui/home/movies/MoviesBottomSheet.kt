package com.animsh.moviem.ui.home.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.animsh.moviem.databinding.LayoutBottomSheetMoviesBinding
import com.animsh.moviem.models.movie.Result
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by animsh on 5/8/2021.
 */
class MoviesBottomSheet(
    private val result: Result
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "MOVIESBOTTOMSHEET"
    }

    private var _binding: LayoutBottomSheetMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutBottomSheetMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            data = result
        }
    }

    fun newInstance(): MoviesBottomSheet {
        return MoviesBottomSheet(result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}