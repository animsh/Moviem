package com.animsh.moviem.ui.home.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.animsh.moviem.databinding.ActivityMovieDetailsBinding
import com.animsh.moviem.models.movie.Movie

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val data: Movie? = intent.getParcelableExtra<Movie>("movie")
            movie = data
        }
    }
}