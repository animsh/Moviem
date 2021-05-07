package com.animsh.moviem.ui

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.animsh.moviem.R
import com.animsh.moviem.databinding.ActivityMainBinding
import com.animsh.moviem.ui.comingsoon.ComingSoonFragment
import com.animsh.moviem.ui.home.HomeFragment
import com.animsh.moviem.ui.more.MoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeFragment: Fragment = HomeFragment()
    private val comingSoonFragment: Fragment = ComingSoonFragment()
    private val moreFragment: Fragment = MoreFragment()
    private val fragmentManager: FragmentManager = supportFragmentManager
    private var active = homeFragment
    private var backStack: MutableList<Fragment> = Stack()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            supportActionBar?.hide()
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                statusBarColor = Color.TRANSPARENT
            }
            setupBottomNav()
        }
    }

    private fun setupBottomNav() {
        backStack.add(homeFragment)
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        }
        if (fragmentManager.findFragmentByTag("0") == null) {
            fragmentManager.beginTransaction().add(R.id.main_container, moreFragment, "2")
                .hide(moreFragment).commit()
            fragmentManager.beginTransaction().add(R.id.main_container, comingSoonFragment, "1")
                .hide(comingSoonFragment).commit()
            fragmentManager.beginTransaction().add(R.id.main_container, homeFragment, "0")
                .commit()
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    fragmentManager.beginTransaction().hide(active).show(homeFragment).commit()
                    active = homeFragment
                    if (backStack[backStack.lastIndex] != active)
                        backStack.add(active)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.comingSoonFragment -> {
                    fragmentManager.beginTransaction().hide(active).show(comingSoonFragment)
                        .commit()
                    active = comingSoonFragment
                    if (backStack[backStack.lastIndex] != active)
                        backStack.add(active)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.moreFragment -> {
                    fragmentManager.beginTransaction().hide(active).show(moreFragment).commit()
                    active = moreFragment
                    if (backStack[backStack.lastIndex] != active)
                        backStack.add(active)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.bottomNavigationView.apply {
            setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        }
        binding.bottomNavigationView.menu.getItem(0).isChecked = true
    }

    override fun onBackPressed() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
            fragmentManager.beginTransaction().hide(active).show(backStack[backStack.lastIndex])
                .commit()
            active = backStack[backStack.lastIndex]
            binding.bottomNavigationView.menu.getItem(active.tag!!.toInt()).isChecked = true
        } else {
            super.onBackPressed()
        }
    }
}