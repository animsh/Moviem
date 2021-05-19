package com.animsh.moviem.ui.search

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.animsh.moviem.R
import com.animsh.moviem.adapters.MovieAdapter
import com.animsh.moviem.adapters.TvAdapter
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.ActivitySearchBinding
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.Constants.Companion.modeChecker
import com.animsh.moviem.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.android.synthetic.main.layout_season_selector.view.*

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var tvViewModel: TvViewModel

    private val moviesAdapter by lazy { MovieAdapter(supportFragmentManager) }
    private val tvAdapter by lazy { TvAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        tvViewModel = ViewModelProvider(this).get(TvViewModel::class.java)
        tvViewModel.setSearchChoice("Movies")
        binding.apply {
            searchRecyclerview.layoutManager =
                GridLayoutManager(this@SearchActivity, 3, GridLayoutManager.VERTICAL, false)
            searchBtn.setOnClickListener {
                searchView.visibility = View.VISIBLE
                searchView.isIconified = false
                searchBtn.visibility = View.INVISIBLE
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    tvViewModel.searchChoice.observe(this@SearchActivity, {
                        searchApiData(query, it)
                    })
                    return true
                }
            })

            tvViewModel.searchChoice.observe(this@SearchActivity, {
                choiceBtn.text = it
                if (it == "Movies")
                    searchRecyclerview.adapter = moviesAdapter
                else
                    searchRecyclerview.adapter = tvAdapter
            })

            choiceBtn.setOnClickListener {
                showChoiceDialog()
            }

            val clearButton: ImageView = searchView.findViewById(R.id.search_close_btn)
            clearButton.setOnClickListener { v ->
                if (searchView.query.isEmpty()) {
                    searchView.isIconified = true
                    searchView.visibility = View.INVISIBLE
                    searchBtn.visibility = View.VISIBLE
                } else {
                    // Do your task here
                    searchView.setQuery("", false)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.searchView.visibility == View.VISIBLE) {
            searchView.isIconified = true
            searchView.visibility = View.INVISIBLE
            searchBtn.visibility = View.VISIBLE
        } else {
            super.onBackPressed()
        }
    }

    private fun searchApiData(query: String, choice: String) {
        if (choice == "Movies") {
            moviesViewModel.searchMovies(Constants.API_KEY, query)
            moviesViewModel.searchMovieResponse.observe(this, { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        binding.searchRecyclerview.hideShimmer()
                        binding.animationView.visibility = View.INVISIBLE
                        binding.subText.visibility = View.INVISIBLE
                        response.data?.let {
                            moviesAdapter.setCommonData(it)
                            Log.d("LOGDATA", "requestApiData: 1")
                        }
                    }
                    is NetworkResult.Error -> {
                        binding.searchRecyclerview.hideShimmer()
                        binding.animationView.visibility = View.VISIBLE
                        binding.subText.visibility = View.VISIBLE
                        binding.subText.text = response.message
                        Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                    }
                    is NetworkResult.Loading -> {
                        binding.animationView.visibility = View.INVISIBLE
                        binding.subText.visibility = View.INVISIBLE
                        binding.searchRecyclerview.showShimmer()
                        Log.d("LOGDATA", "requestApiData: 3")
                    }
                }
            })
        } else {
            tvViewModel.searchTvShow(Constants.API_KEY, query)
            tvViewModel.searchResponse.observe(this, { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        binding.searchRecyclerview.hideShimmer()
                        binding.animationView.visibility = View.INVISIBLE
                        binding.subText.visibility = View.INVISIBLE
                        response.data?.let {
                            tvAdapter.setData(it)
                            Log.d("LOGDATA", "requestApiData: 1")
                        }
                    }
                    is NetworkResult.Error -> {
                        binding.searchRecyclerview.hideShimmer()
                        binding.animationView.visibility = View.VISIBLE
                        binding.subText.visibility = View.VISIBLE
                        binding.subText.text = response.message
                        Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                    }
                    is NetworkResult.Loading -> {
                        binding.animationView.visibility = View.INVISIBLE
                        binding.subText.visibility = View.INVISIBLE
                        binding.searchRecyclerview.showShimmer()
                        Log.d("LOGDATA", "requestApiData: 3")
                    }
                }
            })
        }

    }

    private fun showChoiceDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val layout: View = layoutInflater
            .inflate(
                R.layout.layout_season_selector,
                null
            )
        builder.setView(layout)
        val font: Typeface =
            ResourcesCompat.getFont(this, R.font.netflix_sans_regular)!!
        val dialog: AlertDialog = builder.create()

        for (i in 1..2) {
            val tv = TextView(this)
            if (i == 1) {
                tv.text = "Movies"
            } else {
                tv.text = "TV Shows"
            }
            tv.id = i + 10
            tv.textSize = 18F
            tv.gravity = Gravity.CENTER
            tv.height = 90
            tv.setTypeface(font, Typeface.NORMAL)
            if (modeChecker(this)) {
                tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.subtitleTextColorDark
                    )
                )
            } else {
                tv.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.subtitleTextColorLight
                    )
                )
            }
            tvViewModel.searchChoice.observe(this, { choice ->
                if (tv.text == choice) {
                    tv.textSize = 24F
                    tv.setTypeface(font, Typeface.BOLD)
                    if (modeChecker(this)) {
                        tv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.titleTextColorDark
                            )
                        )
                    } else {
                        tv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.titleTextColorDark
                            )
                        )
                    }
                }
            })
            tv.setOnClickListener {
                tvViewModel.setSearchChoice(tv.text.toString())
                dialog.dismiss()
            }
            // name in seasonCollection because we are using same layout of seasons
            layout.seasonsCollection.addView(tv)
        }

        dialog.show()
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog.window?.setDimAmount(0.8f)
    }


}