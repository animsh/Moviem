package com.animsh.moviem.ui.home.tvs

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
import com.animsh.moviem.data.database.entity.FavoriteTVEntity
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.LayoutBottomSheetTvBinding
import com.animsh.moviem.models.tv.Result
import com.animsh.moviem.ui.home.movies.MoviesBottomSheet
import com.animsh.moviem.ui.home.tvs.details.TVDetailsActivity
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

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

    private var tvSaved: Boolean = false
    private var savedTVId: Int = 0

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
            requestApiData(result.id!!)
            Log.d(TAG, "onViewCreated: " + result.id)
            infoBtn.setOnClickListener {
                val intent: Intent = Intent(context, TVDetailsActivity::class.java)
                intent.putExtra("tv", binding.data)
                context?.startActivity(intent)
                dismiss()
            }

            shareBtn.setOnClickListener {
                Picasso.get()
                    .load(Constants.IMAGE_W500 + binding.data?.posterPath)
                    .into(object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "image/*"
                            intent.putExtra(
                                Intent.EXTRA_STREAM,
                                Constants.getLocalBitmapUri(
                                    bitmap!!,
                                    context!!,
                                    binding.data?.name!!
                                )
                            )
                            val dataText = "${binding.data?.name!!}\n${binding.data?.homepage!!}"
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                dataText
                            )
                            startActivity(Intent.createChooser(intent, "Share TV Show"))
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            Log.d(MoviesBottomSheet.TAG, "onBitmapFailed: " + e?.message)
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            val dataText = "${binding.data?.name!!}\n${binding.data?.homepage!!}"
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                dataText
                            )
                            startActivity(Intent.createChooser(intent, "Share TV Show"))
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                    })
            }

            addToMyList.setOnClickListener {
                if (tvSaved) {
                    removeFromFav(addToMyList)
                } else {
                    saveToFav(addToMyList)
                }
            }

            checkFavTVStatus()
        }
    }

    private fun saveToFav(addToMyList: MaterialButton) {
        val favoriteTVEntity = FavoriteTVEntity(0, binding.data!!)
        tvViewModel.insertFavTV(favoriteTVEntity)
        addToMyList.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_check, null)
        showMessage("added to my list!!")
        tvSaved = true
    }

    private fun removeFromFav(addToMyList: MaterialButton) {
        val favoriteTVEntity = FavoriteTVEntity(savedTVId, binding.data!!)
        tvViewModel.deleteFavTV(favoriteTVEntity)
        addToMyList.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_add, null)
        showMessage("removed from my list!!")
        tvSaved = false
    }

    private fun checkFavTVStatus() {
        tvViewModel.readFavTV.observe(this, { favoriteEntity ->
            try {
                for (savedTv in favoriteEntity) {
                    if (savedTv.result.id == result.id) {
                        binding.addToMyList.icon =
                            ResourcesCompat.getDrawable(resources, R.drawable.ic_check, null)
                        tvSaved = true
                        savedTVId = savedTv.id
                    }
                }
            } catch (e: Exception) {
                Log.d("TAGTAGTAG", "checkFavTVStatus: " + e.message)
            }
        })
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

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.layoutBottomSheetTv,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

}