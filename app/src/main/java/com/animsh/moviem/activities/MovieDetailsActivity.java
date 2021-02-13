package com.animsh.moviem.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ActivityMovieDetailsBinding;
import com.animsh.moviem.viewmodels.moviesviewmodel.MovieDetailsVieModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;
    private MovieDetailsVieModel movieDetailsVieModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        movieDetailsVieModel = new ViewModelProvider(this).get(MovieDetailsVieModel.class);
        int movieId = getIntent().getIntExtra("movieId", -1);
        getMovieDetails(movieId);
    }

    void getMovieDetails(int movieId) {
        movieDetailsVieModel.getMovieDetails(movieId, getString(R.string.api_key)).observe(this, movieDetailResponse -> {
            Toast.makeText(this, "Name: " + movieDetailResponse.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }
}