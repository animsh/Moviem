package com.animsh.moviem.ui.home.movies.details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.animsh.moviem.adapters.ViewPagerAdapter
import com.animsh.moviem.databinding.ActivityMovieDetailsBinding
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.ui.home.movies.MoviesBottomSheet
import com.animsh.moviem.util.Constants
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val data: Movie? = intent.getParcelableExtra<Movie>("movie")
            movie = data
            tabLayout.setupWithViewPager(viewPager)
            viewPager.offscreenPageLimit = 3
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
            viewPagerAdapter.addFragment(SRMoviesFragment(data!!.id, 0), "More Like This")
            viewPagerAdapter.addFragment(SRMoviesFragment(data.id, 1), "Recommendations")
            viewPagerAdapter.addFragment(MovieCreditsFragment(data.id), "Crew & Cast")
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
                                    this@MovieDetailsActivity,
                                    data.title!!
                                )
                            )
                            val dataText = "${data.title}\n${data.homepage!!}"
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                dataText
                            )
                            startActivity(Intent.createChooser(intent, "Share Movie"))
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            Log.d(MoviesBottomSheet.TAG, "onBitmapFailed: " + e?.message)
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            val dataText = "${data.title}\n${data.homepage!!}"
                            intent.putExtra(
                                Intent.EXTRA_TEXT,
                                dataText
                            )
                            startActivity(Intent.createChooser(intent, "Share Movie"))
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                    })
            }
        }
    }

}