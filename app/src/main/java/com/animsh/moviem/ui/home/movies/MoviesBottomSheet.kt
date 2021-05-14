package com.animsh.moviem.ui.home.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.databinding.LayoutBottomSheetMoviesBinding
import com.animsh.moviem.models.movie.Result
import com.animsh.moviem.ui.home.movies.details.MovieDetailsActivity
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
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
    private lateinit var moviesViewModel: MoviesViewModel

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
        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        binding.apply {
            requestApiData(result.id)
            infoBtn.setOnClickListener {
                val intent: Intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra("movie", binding.data)
                context?.startActivity(intent)
            }
        }
    }

    fun newInstance(): MoviesBottomSheet {
        return MoviesBottomSheet(result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestApiData(movieId: Int) {
        moviesViewModel.getMovieDetails(movieId, Constants.API_KEY).invokeOnCompletion {
            Log.d(
                "TAGTAGTAG",
                "requestApiData: " + moviesViewModel.movieDetailsResponse.value?.data?.id
            )
        }
        moviesViewModel.movieDetailsResponse.observe(viewLifecycleOwner, { response ->
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