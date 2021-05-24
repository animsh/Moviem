package com.animsh.moviem.ui.home.tvs

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.animsh.moviem.R
import com.animsh.moviem.adapters.TvAdapter
import com.animsh.moviem.data.database.entity.FavoriteTVEntity
import com.animsh.moviem.data.viewmodels.TvViewModel
import com.animsh.moviem.databinding.FragmentTvShowsBinding
import com.animsh.moviem.ui.home.movies.MoviesBottomSheet
import com.animsh.moviem.ui.home.tvs.details.TVDetailsActivity
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.Constants.Companion.API_KEY
import com.animsh.moviem.util.NetworkResult
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_tv_shows.view.*

class TvShowsFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var fragmentTvShowsBinding: FragmentTvShowsBinding
    private val trendingTvAdapter by lazy { TvAdapter(childFragmentManager) }
    private val onAirTvAdapter by lazy { TvAdapter(childFragmentManager) }
    private val airingTodayTvAdapter by lazy { TvAdapter(childFragmentManager) }
    private val popularTvAdapter by lazy { TvAdapter(childFragmentManager) }
    private val topTvAdapter by lazy { TvAdapter(childFragmentManager) }
    private lateinit var tvViewModel: TvViewModel

    private var tvSaved: Boolean = false
    private var savedTVId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        mView = fragmentTvShowsBinding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvViewModel = ViewModelProvider(requireActivity()).get(TvViewModel::class.java)
        setupRecyclerView()
        requestApiData()
        fragmentTvShowsBinding.apply {
            refreshLayout.setOnRefreshListener {
                showShimmer()
                trendingTvAdapter.clearList()
                popularTvAdapter.clearList()
                topTvAdapter.clearList()
                onAirTvAdapter.clearList()
                airingTodayTvAdapter.clearList()
                requestApiData()
                refreshLayout.isRefreshing = false
            }

            addToMyList.setOnClickListener {
                if (tvSaved) {
                    removeFromFav(addToMyList)
                } else {
                    saveToFav(addToMyList)
                }
            }
        }
    }


    private fun saveToFav(addToMyList: TextView) {
        val favoriteTVEntity = FavoriteTVEntity(0, fragmentTvShowsBinding.latestTv!!)
        tvViewModel.insertFavTV(favoriteTVEntity)
        addToMyList.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_check, 0, 0)
        showMessage("added to my list!!")
        tvSaved = true
    }

    private fun removeFromFav(addToMyList: TextView) {
        val favoriteTVEntity = FavoriteTVEntity(savedTVId, fragmentTvShowsBinding.latestTv!!)
        tvViewModel.deleteFavTV(favoriteTVEntity)
        addToMyList.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add, 0, 0)
        showMessage("removed from my list!!")
        tvSaved = false
    }

    private fun checkFavTVStatus() {
        tvViewModel.readFavTV.observe(requireActivity(), { favoriteEntity ->
            try {
                for (savedTv in favoriteEntity) {
                    if (savedTv.result.id == fragmentTvShowsBinding.latestTv!!.id) {
                        fragmentTvShowsBinding.addToMyList.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            R.drawable.ic_check,
                            0,
                            0
                        )
                        tvSaved = true
                        savedTVId = savedTv.id
                        break
                    } else {
                        fragmentTvShowsBinding.addToMyList.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            R.drawable.ic_add,
                            0,
                            0
                        )
                        tvSaved = false
                        savedTVId = 0
                    }
                }
            } catch (e: Exception) {
                Log.d("TAGTAGTAG", "checkFavTVStatus: " + e.message)
            }
        })
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            fragmentTvShowsBinding.refreshLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun requestApiData() {
        tvViewModel.getLatestTv(API_KEY).invokeOnCompletion {

        }
        tvViewModel.latestTvResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.posterShimmer.hideShimmer()
                    mView.posterShimmer.visibility = View.GONE
                    response.data?.let {
                        fragmentTvShowsBinding.latestTv = it
                        fragmentTvShowsBinding.latestTVInfo.setOnClickListener { view ->
                            val intent: Intent = Intent(context, TVDetailsActivity::class.java)
                            intent.putExtra("tv", it)
                            context?.startActivity(intent)
                        }
                        fragmentTvShowsBinding.shareBtn.setOnClickListener { view ->
                            Picasso.get()
                                .load(Constants.IMAGE_W500 + it.posterPath)
                                .into(object : Target {
                                    override fun onBitmapLoaded(
                                        bitmap: Bitmap?,
                                        from: Picasso.LoadedFrom?
                                    ) {
                                        val intent = Intent(Intent.ACTION_SEND)
                                        intent.type = "image/*"
                                        intent.putExtra(
                                            Intent.EXTRA_STREAM,
                                            Constants.getLocalBitmapUri(
                                                bitmap!!,
                                                requireContext(),
                                                it.name!!
                                            )
                                        )
                                        val dataText = "${it.name}\n${it.homepage}"
                                        intent.putExtra(
                                            Intent.EXTRA_TEXT,
                                            dataText
                                        )
                                        startActivity(Intent.createChooser(intent, "Share Movie"))
                                    }

                                    override fun onBitmapFailed(
                                        e: Exception?,
                                        errorDrawable: Drawable?
                                    ) {
                                        Log.d(
                                            MoviesBottomSheet.TAG,
                                            "onBitmapFailed: " + e?.message
                                        )
                                        val intent = Intent(Intent.ACTION_SEND)
                                        intent.type = "text/plain"
                                        val dataText = "${it.name}\n${it.homepage}"
                                        intent.putExtra(
                                            Intent.EXTRA_TEXT,
                                            dataText
                                        )
                                        startActivity(Intent.createChooser(intent, "Share Movie"))
                                    }

                                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                                })
                        }
                        checkFavTVStatus()
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.posterShimmer.hideShimmer()
                    mView.posterShimmer.visibility = View.GONE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.posterShimmer.visibility = View.VISIBLE
                    mView.posterShimmer.showShimmer(true)
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getTrendingTv(API_KEY, 1)
        tvViewModel.trendingResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.trendingTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        trendingTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.trendingTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.trendingTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getPopularTv(API_KEY, 1)
        tvViewModel.popularTvOResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.popularTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        popularTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.popularTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.popularTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getTopTv(API_KEY, 1)
        tvViewModel.topTvResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.topTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        topTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.topTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.topTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getAiringToday(API_KEY, 1)
        tvViewModel.tvAiringTodayResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.airingTodayTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        airingTodayTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.airingTodayTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.airingTodayTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        tvViewModel.getOnAirTv(API_KEY, 1)
        tvViewModel.tvOnAirResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.onAirTvShowRecyclerview.hideShimmer()
                    response.data?.let {
                        onAirTvAdapter.setData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.onAirTvShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.onAirTvShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        mView.trendingTvShowRecyclerview.adapter = trendingTvAdapter
        mView.trendingTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.topTvShowRecyclerview.adapter = topTvAdapter
        mView.topTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.popularTvShowRecyclerview.adapter = popularTvAdapter
        mView.popularTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.onAirTvShowRecyclerview.adapter = onAirTvAdapter
        mView.onAirTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.airingTodayTvShowRecyclerview.adapter = airingTodayTvAdapter
        mView.airingTodayTvShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        showShimmer()
    }

    fun showShimmer() {
        mView.posterShimmer.showShimmer(true)
        mView.trendingTvShowRecyclerview.showShimmer()
        mView.airingTodayTvShowRecyclerview.showShimmer()
        mView.onAirTvShowRecyclerview.showShimmer()
        mView.popularTvShowRecyclerview.showShimmer()
        mView.topTvShowRecyclerview.showShimmer()
    }
}