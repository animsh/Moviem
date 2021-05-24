package com.animsh.moviem.ui.home.movies

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.animsh.moviem.R
import com.animsh.moviem.adapters.MovieAdapter
import com.animsh.moviem.data.database.entity.FavoriteMovieEntity
import com.animsh.moviem.data.viewmodels.MoviesViewModel
import com.animsh.moviem.databinding.FragmentMoviesBinding
import com.animsh.moviem.ui.home.movies.details.MovieDetailsActivity
import com.animsh.moviem.util.Constants
import com.animsh.moviem.util.Constants.Companion.API_KEY
import com.animsh.moviem.util.NetworkResult
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.view.*

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var fragmentMoviesBinding: FragmentMoviesBinding
    private val trendingMoviesAdapter by lazy { MovieAdapter(childFragmentManager) }
    private val popularMoviesAdapter by lazy { MovieAdapter(childFragmentManager) }
    private val topMoviesAdapter by lazy { MovieAdapter(childFragmentManager) }
    private val nowPlayingMoviesAdapter by lazy { MovieAdapter(childFragmentManager) }
    private lateinit var moviesViewModel: MoviesViewModel

    private var movieSaved: Boolean = false
    private var savedMovieId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        mView = fragmentMoviesBinding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel = ViewModelProvider(requireActivity()).get(MoviesViewModel::class.java)
        setupRecyclerView()
        requestApiData()

        fragmentMoviesBinding.apply {
            refreshLayout.setOnRefreshListener {
                showShimmer()
                topMoviesAdapter.clearList()
                popularMoviesAdapter.clearList()
                nowPlayingMoviesAdapter.clearList()
                trendingMoviesAdapter.clearList()
                requestApiData()
                refreshLayout.isRefreshing = false
            }

            addToMyList.setOnClickListener {
                if (movieSaved) {
                    removeFromFav(addToMyList)
                } else {
                    saveToFav(addToMyList)
                }
            }
        }

    }

    private fun saveToFav(addToMyList: TextView) {
        val favoriteMovieEntity = FavoriteMovieEntity(0, fragmentMoviesBinding.latestMovie!!)
        moviesViewModel.insertFavMovie(favoriteMovieEntity)
        addToMyList.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_check, 0, 0)
        showMessage("added to my list!!")
        movieSaved = true
    }

    private fun removeFromFav(addToMyList: TextView) {
        val favoriteMovieEntity = FavoriteMovieEntity(
            savedMovieId,
            fragmentMoviesBinding.latestMovie!!
        )
        moviesViewModel.deleteFavMovie(favoriteMovieEntity)
        addToMyList.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add, 0, 0)
        showMessage("removed from my list!!")
        movieSaved = false
    }

    private fun checkFavMovieStatus() {
        moviesViewModel.readFavMovie.observe(requireActivity(), { favoriteEntity ->
            try {
                for (savedMovie in favoriteEntity) {
                    if (savedMovie.result.id == fragmentMoviesBinding.latestMovie!!.id) {
                        fragmentMoviesBinding.addToMyList.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            R.drawable.ic_check,
                            0,
                            0
                        )
                        movieSaved = true
                        savedMovieId = savedMovie.id
                        break
                    } else {
                        fragmentMoviesBinding.addToMyList.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            R.drawable.ic_add,
                            0,
                            0
                        )
                        movieSaved = false
                        savedMovieId = 0
                    }
                }
            } catch (e: Exception) {
                Log.d("TAGTAGTAG", "checkFavMovieStatus: " + e.message)
            }
        })
    }


    private fun requestApiData() {
        moviesViewModel.getLatestMovie(API_KEY)
        moviesViewModel.latestMovieResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.posterShimmer.hideShimmer()
                    mView.posterShimmer.visibility = GONE
                    response.data?.let {
                        fragmentMoviesBinding.latestMovie = it
                        fragmentMoviesBinding.latestMovieInfo.setOnClickListener { view ->
                            val intent: Intent = Intent(context, MovieDetailsActivity::class.java)
                            intent.putExtra("movie", it)
                            context?.startActivity(intent)
                        }
                        fragmentMoviesBinding.shareBtn.setOnClickListener { view ->
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
                                                it.title!!
                                            )
                                        )
                                        val dataText = "${it.title}\n${it.homepage}"
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
                                        val dataText = "${it.title}\n${it.homepage}"
                                        intent.putExtra(
                                            Intent.EXTRA_TEXT,
                                            dataText
                                        )
                                        startActivity(Intent.createChooser(intent, "Share Movie"))
                                    }

                                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                                })
                        }
                        checkFavMovieStatus()
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.posterShimmer.hideShimmer()
                    mView.posterShimmer.visibility = GONE
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.posterShimmer.visibility = VISIBLE
                    mView.posterShimmer.showShimmer(true)
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })


        moviesViewModel.getTrendingMovies(API_KEY, 1)
        moviesViewModel.trendingMovieResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.trendingMoviesShowRecyclerview.hideShimmer()
                    response.data?.let {
                        trendingMoviesAdapter.setCommonData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.trendingMoviesShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.trendingMoviesShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        moviesViewModel.getPopularMovies(API_KEY, 1)
        moviesViewModel.popularMovieResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.popularMoviesShowRecyclerview.hideShimmer()
                    response.data?.let {
                        popularMoviesAdapter.setCommonData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.popularMoviesShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.popularMoviesShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        moviesViewModel.getTopMovies(API_KEY, 1)
        moviesViewModel.topMoviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.topMoviesShowRecyclerview.hideShimmer()
                    response.data?.let {
                        topMoviesAdapter.setCommonData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.topMoviesShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.topMoviesShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })

        moviesViewModel.getNowPlayingMovies(API_KEY, 1)
        moviesViewModel.nowPlayingMoviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mView.nowPlayingMoviesShowRecyclerview.hideShimmer()
                    response.data?.let {
                        nowPlayingMoviesAdapter.setUniqueData(it)
                        Log.d("LOGDATA", "requestApiData: 1")
                    }
                }
                is NetworkResult.Error -> {
                    mView.nowPlayingMoviesShowRecyclerview.hideShimmer()
                    Log.d("LOGDATA", "requestApiData: " + response.message.toString())
                }
                is NetworkResult.Loading -> {
                    mView.nowPlayingMoviesShowRecyclerview.showShimmer()
                    Log.d("LOGDATA", "requestApiData: 3")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        mView.trendingMoviesShowRecyclerview.adapter = trendingMoviesAdapter
        mView.trendingMoviesShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.topMoviesShowRecyclerview.adapter = topMoviesAdapter
        mView.topMoviesShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.popularMoviesShowRecyclerview.adapter = popularMoviesAdapter
        mView.popularMoviesShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        mView.nowPlayingMoviesShowRecyclerview.adapter = nowPlayingMoviesAdapter
        mView.nowPlayingMoviesShowRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        showShimmer()
    }

    private fun showShimmer() {
        mView.trendingMoviesShowRecyclerview.showShimmer()
        mView.nowPlayingMoviesShowRecyclerview.showShimmer()
        mView.popularMoviesShowRecyclerview.showShimmer()
        mView.topMoviesShowRecyclerview.showShimmer()
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            fragmentMoviesBinding.refreshLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }
}