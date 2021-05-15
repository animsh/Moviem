package com.animsh.moviem.adapters

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by animsh on 5/15/2021.
 */
class ViewPagerAdapter(fm: FragmentManager, behavior: Int) :
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