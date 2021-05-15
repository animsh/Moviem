package com.animsh.moviem.ui.home.tvs.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.animsh.moviem.adapters.ViewPagerAdapter
import com.animsh.moviem.databinding.ActivityTvDetailsBinding
import com.animsh.moviem.models.tv.TV
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
            viewPager.offscreenPageLimit = 3
            val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
            viewPagerAdapter.addFragment(SRTVFragment(data!!.id!!, 0), "More Like This")
            viewPagerAdapter.addFragment(SRTVFragment(data.id!!, 1), "Recommendations")
            viewPagerAdapter.addFragment(TvCreditsFragment(data.id), "Crew & Cast")
            viewPager.adapter = viewPagerAdapter
        }
    }
}