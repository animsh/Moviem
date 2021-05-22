package com.animsh.moviem.ui.home.tvs.details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.animsh.moviem.R
import com.animsh.moviem.adapters.ViewPagerAdapter
import com.animsh.moviem.data.database.entity.FavoriteTVEntity
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.ActivityTvDetailsBinding
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.ui.home.movies.MoviesBottomSheet
import com.animsh.moviem.util.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailsBinding
    private lateinit var tvViewModel: TvViewModel
    private var tvSaved: Boolean = false
    private var savedTVId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tvViewModel = ViewModelProvider(this).get(TvViewModel::class.java)
        binding.apply {
            val data: TV? = intent.getParcelableExtra<TV>("tv")
            tv = data
            tabLayout.setupWithViewPager(viewPager)
            viewPager.offscreenPageLimit = 4
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
            viewPagerAdapter.addFragment(
                SeasonsFragment(data!!.id!!, data.numberOfSeasons!!),
                "Episodes"
            )
            viewPagerAdapter.addFragment(SRTVFragment(data.id!!, 0), "More Like This")
            viewPagerAdapter.addFragment(SRTVFragment(data.id, 1), "Recommendations")
            viewPagerAdapter.addFragment(TvCreditsFragment(data.id), "Crew & Cast")
            viewPager.adapter = viewPagerAdapter

            shareBtn.setOnClickListener {
                Picasso.get()
                    .load(Constants.IMAGE_W500 + data.posterPath)
                    .into(object : Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "image/*"
                            intent.putExtra(
                                Intent.EXTRA_STREAM,
                                Constants.getLocalBitmapUri(
                                    bitmap!!,
                                    this@TVDetailsActivity,
                                    data.name!!
                                )
                            )
                            val dataText = "${data.name}\n${data.homepage!!}"
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
                            val dataText = "${data.name}\n${data.homepage!!}"
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
        val favoriteTVEntity = FavoriteTVEntity(0, binding.tv!!)
        tvViewModel.insertFavTV(favoriteTVEntity)
        addToMyList.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_check, null)
        showMessage("added to my list!!")
        tvSaved = true
    }

    private fun removeFromFav(addToMyList: MaterialButton) {
        val favoriteTVEntity = FavoriteTVEntity(savedTVId, binding.tv!!)
        tvViewModel.deleteFavTV(favoriteTVEntity)
        addToMyList.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_add, null)
        showMessage("removed from my list!!")
        tvSaved = false
    }

    private fun checkFavTVStatus() {
        tvViewModel.readFavTV.observe(this, { favoriteEntity ->
            try {
                for (savedTv in favoriteEntity) {
                    if (savedTv.result.id == binding.tv!!.id) {
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

    private fun showMessage(message: String) {
        Snackbar.make(
            binding.layoutTVDetails,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

}