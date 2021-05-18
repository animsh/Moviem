package com.animsh.moviem.ui.home.tvs.details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.animsh.moviem.adapters.ViewPagerAdapter
import com.animsh.moviem.databinding.ActivityTvDetailsBinding
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.ui.home.movies.MoviesBottomSheet
import com.animsh.moviem.util.Constants
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        }
    }
}