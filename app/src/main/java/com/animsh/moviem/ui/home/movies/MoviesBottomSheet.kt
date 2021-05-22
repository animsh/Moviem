package com.animsh.moviem.ui.home.movies

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.animsh.moviem.R
import com.animsh.moviem.data.database.entity.FavoriteMovieEntity
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.databinding.LayoutBottomSheetMoviesBinding
import com.animsh.moviem.models.movie.Result
import com.animsh.moviem.ui.home.movies.details.MovieDetailsActivity
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.Constants.Companion.IMAGE_W500
import com.animsh.moviem.util.Constants.Companion.getLocalBitmapUri
import com.animsh.moviem.util.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target


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

    private var movieSaved: Boolean = false
    private var savedMovieId: Int = 0

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
                dismiss()
            }

            shareBtn.setOnClickListener {
                Picasso.get()
                    .load(IMAGE_W500 + binding.data?.posterPath)
                    .into(object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "image/*"
                            intent.putExtra(
                                Intent.EXTRA_STREAM,
                                getLocalBitmapUri(bitmap!!, context!!, binding.data?.title!!)
                            )
                            val dataText = "${binding.data?.title!!}\n${binding.data?.homepage!!}"
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                dataText
                            )
                            startActivity(Intent.createChooser(intent, "Share Movie"))
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            Log.d(TAG, "onBitmapFailed: " + e?.message)
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            val dataText = "${binding.data?.title!!}\n${binding.data?.homepage!!}"
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                dataText
                            )
                            startActivity(Intent.createChooser(intent, "Share Movie"))
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                    })
            }

            addToMyList.setOnClickListener {
                if (movieSaved) {
                    removeFromFav(addToMyList)
                } else {
                    saveToFav(addToMyList)
                }
            }

            checkFavMovieStatus()
        }
    }

    private fun saveToFav(addToMyList: MaterialButton) {
        val favoriteMovieEntity = FavoriteMovieEntity(0, binding.data!!)
        moviesViewModel.insertFavMovie(favoriteMovieEntity)
        addToMyList.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_check, null)
        showMessage("added to my list!!")
        movieSaved = true
    }

    private fun removeFromFav(addToMyList: MaterialButton) {
        val favoriteMovieEntity = FavoriteMovieEntity(savedMovieId, binding.data!!)
        moviesViewModel.deleteFavMovie(favoriteMovieEntity)
        addToMyList.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_add, null)
        showMessage("removed from my list!!")
        movieSaved = false
    }

    private fun checkFavMovieStatus() {
        moviesViewModel.readFavMovie.observe(this, { favoriteEntity ->
            try {
                for (savedMovie in favoriteEntity) {
                    if (savedMovie.result.id == result.id) {
                        binding.addToMyList.icon =
                            ResourcesCompat.getDrawable(resources, R.drawable.ic_check, null)
                        movieSaved = true
                        savedMovieId = savedMovie.id
                    }
                }
            } catch (e: Exception) {
                Log.d("TAGTAGTAG", "checkFavMovieStatus: " + e.message)
            }
        })
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
                "requestApiData: $movieId"
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


    private fun showMessage(message: String) {
        Snackbar.make(
            binding.layoutBottomSheetMovies,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }
}