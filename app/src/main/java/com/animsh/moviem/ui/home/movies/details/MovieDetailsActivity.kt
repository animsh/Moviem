package com.animsh.moviem.ui.home.movies.details

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
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
            viewPagerAdapter.addFragment(CreditsFragment(data.id), "Crew & Cast")
            viewPager.adapter = viewPagerAdapter
        }
    }

    private class ViewPagerAdapter(fm: FragmentManager, behavior: Int) :
        FragmentPagerAdapter(fm, behavior) {
        var fragmentList: MutableList<Fragment> = ArrayList()
        var titleList: MutableList<String> = ArrayList()
        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        @Nullable
        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}