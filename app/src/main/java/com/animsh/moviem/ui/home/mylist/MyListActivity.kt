package com.animsh.moviem.ui.home.mylist

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.animsh.moviem.R
import com.animsh.moviem.adapters.FavMovieAdapter
import com.animsh.moviem.adapters.FavTvAdapter
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.ActivityMyListBinding
import com.animsh.moviem.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_season_selector.view.*


@AndroidEntryPoint
class MyListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyListBinding

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var tvViewModel: TvViewModel

    private val moviesAdapter by lazy { FavMovieAdapter(this, moviesViewModel) }
    private val tvAdapter by lazy { FavTvAdapter(this, tvViewModel) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        tvViewModel = ViewModelProvider(this).get(TvViewModel::class.java)
        tvViewModel.setMyListChoice("Movies")
        binding.apply {
            myListRecyclerview.layoutManager =
                GridLayoutManager(this@MyListActivity, 3, GridLayoutManager.VERTICAL, false)

            choiceBtn.setOnClickListener {
                showChoiceDialog()
            }

            tvViewModel.myListChoice.observe(this@MyListActivity, {
                choiceBtn.text = it
                setData(it)
            })
        }
    }

    private fun setData(choice: String?) {
        if (choice == "Movies") {
            binding.myListRecyclerview.adapter = moviesAdapter
            moviesViewModel.readFavMovie.observe(this, { favEntity ->
                if (favEntity.isNotEmpty()) {
                    binding.animationView.visibility = View.GONE
                    binding.subText.visibility = View.GONE
                } else {
                    binding.animationView.visibility = View.VISIBLE
                    binding.subText.text = "Add movies in your list to get them here!"
                    binding.subText.visibility = View.VISIBLE
                }
                moviesAdapter.setListData(favEntity)
            })
        } else {
            binding.myListRecyclerview.adapter = tvAdapter
            tvViewModel.readFavTV.observe(this, { favEntity ->
                if (favEntity.isNotEmpty()) {
                    binding.animationView.visibility = View.GONE
                    binding.subText.visibility = View.GONE
                } else {
                    binding.animationView.visibility = View.VISIBLE
                    binding.subText.text = "Add tv shows in your list to get them here!"
                    binding.subText.visibility = View.VISIBLE
                }
                tvAdapter.setData(favEntity)
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
            if (Constants.modeChecker(this)) {
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
            tvViewModel.myListChoice.observe(this, { choice ->
                if (tv.text == choice) {
                    tv.textSize = 24F
                    tv.setTypeface(font, Typeface.BOLD)
                    if (Constants.modeChecker(this)) {
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
                tvViewModel.setMyListChoice(tv.text.toString())
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