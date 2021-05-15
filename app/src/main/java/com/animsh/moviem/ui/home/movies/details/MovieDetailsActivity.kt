package com.animsh.moviem.ui.home.movies.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.animsh.moviem.adapters.ViewPagerAdapter
import com.animsh.moviem.databinding.ActivityMovieDetailsBinding
import com.animsh.moviem.models.movie.Movie
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
        }
    }

}