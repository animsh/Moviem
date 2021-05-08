package com.animsh.moviem.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.animsh.moviem.R
import com.animsh.moviem.databinding.FragmentHomeBinding
import com.animsh.moviem.ui.home.movies.MoviesFragment
import com.animsh.moviem.ui.home.mylist.MyListFragment
import com.animsh.moviem.ui.home.tvs.TvShowsFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        binding.apply {
            toolbar.setBackgroundColor(Color.TRANSPARENT)
            tabLayout.setupWithViewPager(viewPager)
            val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(childFragmentManager)
            viewPagerAdapter.apply {
                addFragment(MoviesFragment(), "Movies")
                addFragment(TvShowsFragment(), "TV Shows")
                addFragment(MyListFragment(), "My List")
            }
            viewPager.apply {
                adapter = viewPagerAdapter
                offscreenPageLimit = 3
            }
        }

    }

    private class ViewPagerAdapter(
        fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager) {

        private var fragments: MutableList<Fragment> = ArrayList()
        private var titles: MutableList<String> = ArrayList()

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }
    }
}