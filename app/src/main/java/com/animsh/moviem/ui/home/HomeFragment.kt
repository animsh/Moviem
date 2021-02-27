package com.animsh.moviem.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.animsh.moviem.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setBackgroundColor(Color.TRANSPARENT)

        tabLayout.setupWithViewPager(viewPager)
        val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(MoviesFragment(), "Movies")
        viewPagerAdapter.addFragment(TvShowsFragment(), "TV Shows")
        viewPagerAdapter.addFragment(MyListFragment(), "My List")
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3

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