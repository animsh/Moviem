package com.animsh.moviem.ui.home.tvs.details

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.animsh.moviem.R
import com.animsh.moviem.adapters.EpisodesAdapter
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.FragmentSeasonsBinding
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.Constants.Companion.modeChecker
import com.animsh.moviem.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_season_selector.view.*


@AndroidEntryPoint
class SeasonsFragment(
    private val tvId: Int,
    private val totalSeasons: Int
) : Fragment() {

    private var _binding: FragmentSeasonsBinding? = null
    private val binding get() = _binding

    private lateinit var tvViewModel: TvViewModel
    private lateinit var episodesAdapter: EpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvViewModel = ViewModelProvider(requireActivity()).get(TvViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeasonsBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            episodesAdapter = EpisodesAdapter()
            this!!.episodeRecyclerview.adapter = episodesAdapter
            episodeRecyclerview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            tvViewModel.setSeason(1)
            tvViewModel.chosenSeason.observe(viewLifecycleOwner, { seasonNumber ->
                requestTVEpisodes(seasonNumber)
                seasonBtn.text = "Season $seasonNumber"
            })
            seasonBtn.setOnClickListener {
                showSeasonDialog()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestTVEpisodes(seasonNumber: Int) {
        tvViewModel.getTVEpisode(tvId, seasonNumber, Constants.API_KEY)
        tvViewModel.seasonResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding!!.episodeRecyclerview.hideShimmer()
                    response.data?.let {
                        episodesAdapter.setData(it.episodes)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    binding!!.episodeRecyclerview.hideShimmer()
                    binding!!.warning.visibility = View.VISIBLE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    binding!!.episodeRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }


    private fun showSeasonDialog() {
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

        for (i in 1..totalSeasons) {
            val tv = TextView(requireContext())
            tv.text = "Season $i"
            tv.id = i + 10
            tv.textSize = 18F
            tv.gravity = Gravity.CENTER
            tv.height = 90
            tv.setTypeface(font, Typeface.NORMAL)
            if (modeChecker(requireContext())) {
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
            tvViewModel.chosenSeason.observe(viewLifecycleOwner, { seasonNumber ->
                if (i == seasonNumber) {
                    tv.textSize = 24F
                    tv.setTypeface(font, Typeface.BOLD)
                    if (modeChecker(requireContext())) {
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
                tvViewModel.setSeason(tv.id - 10)
                dialog.dismiss()
            }
            layout.seasonsCollection.addView(tv)
        }

        dialog.show()
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        dialog.window?.setDimAmount(0.8f)
    }
}