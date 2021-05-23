package com.animsh.moviem.ui.home.mylist

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.animsh.moviem.R
import com.animsh.moviem.adapters.FavMovieAdapter
import com.animsh.moviem.adapters.FavTvAdapter
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.FragmentMyListBinding
import com.animsh.moviem.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_season_selector.view.*

@AndroidEntryPoint
class MyListFragment : Fragment() {

    private var _binding: FragmentMyListBinding? = null
    private val binding get() = _binding

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var tvViewModel: TvViewModel

    val moviesAdapter by lazy { FavMovieAdapter(requireActivity(), moviesViewModel) }
    val tvAdapter by lazy { FavTvAdapter(requireActivity(), tvViewModel) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyListBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        tvViewModel = ViewModelProvider(this).get(TvViewModel::class.java)
        tvViewModel.setMyListChoice("Movies")
        binding.apply {
            this!!.myListRecyclerview.layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

            choiceBtn.setOnClickListener {
                showChoiceDialog()
            }

            tvViewModel.myListChoice.observe(viewLifecycleOwner, {
                choiceBtn.text = it
                setData(it)
            })
        }
    }

    private fun setData(choice: String?) {
        if (choice == "Movies") {
            binding!!.myListRecyclerview.adapter = moviesAdapter
            moviesViewModel.readFavMovie.observe(viewLifecycleOwner, { favEntity ->
                if (favEntity.isNotEmpty()) {
                    binding!!.animationView.visibility = View.GONE
                    binding!!.subText.visibility = View.GONE
                } else {
                    binding!!.animationView.visibility = View.VISIBLE
                    binding!!.subText.text = "Add movies in your list to get them here!"
                    binding!!.subText.visibility = View.VISIBLE
                }
                moviesAdapter.setListData(favEntity)
            })
        } else {
            binding!!.myListRecyclerview.adapter = tvAdapter
            tvViewModel.readFavTV.observe(viewLifecycleOwner, { favEntity ->
                if (favEntity.isNotEmpty()) {
                    binding!!.animationView.visibility = View.GONE
                    binding!!.subText.visibility = View.GONE
                } else {
                    binding!!.animationView.visibility = View.VISIBLE
                    binding!!.subText.text = "Add tv shows in your list to get them here!"
                    binding!!.subText.visibility = View.VISIBLE
                }
                tvAdapter.setData(favEntity)
            })
        }
    }

    private fun showChoiceDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val layout: View = layoutInflater
            .inflate(
                R.layout.layout_season_selector,
                null
            )
        builder.setView(layout)
        val font: Typeface =
            ResourcesCompat.getFont(requireContext(), R.font.netflix_sans_regular)!!
        val dialog: AlertDialog = builder.create()

        for (i in 1..2) {
            val tv = TextView(requireContext())
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
            if (Constants.modeChecker(requireContext())) {
                tv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.subtitleTextColorDark
                    )
                )
            } else {
                tv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.subtitleTextColorLight
                    )
                )
            }
            tvViewModel.myListChoice.observe(viewLifecycleOwner, { choice ->
                if (tv.text == choice) {
                    tv.textSize = 24F
                    tv.setTypeface(font, Typeface.BOLD)
                    if (Constants.modeChecker(requireContext())) {
                        tv.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.titleTextColorDark
                            )
                        )
                    } else {
                        tv.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
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

//    override fun setMenuVisibility(visible: Boolean) {
//        if (!visible) {
//            moviesAdapter.removeContextualActionMode()
//            tvAdapter.removeContextualActionMode()
//        }
//        super.setMenuVisibility(visible)
//    }
}