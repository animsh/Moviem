package com.animsh.moviem.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ActivityMovieDetailsBinding;
import com.animsh.moviem.model.Cast;
import com.animsh.moviem.model.Crew;
import com.animsh.moviem.response.moviesresponse.MovieResult;
import com.animsh.moviem.viewmodels.moviesviewmodel.MovieCreditsViewModel;
import com.animsh.moviem.viewmodels.moviesviewmodel.MovieDetailsVieModel;
import com.animsh.moviem.viewmodels.moviesviewmodel.RecommendedMoviesViewModel;
import com.animsh.moviem.viewmodels.moviesviewmodel.SimilarMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;
    private MovieDetailsVieModel movieDetailsVieModel;
    private SimilarMoviesViewModel similarMoviesViewModel;
    private RecommendedMoviesViewModel recommendedMoviesViewModel;
    private MovieCreditsViewModel movieCreditsViewModel;

    private List<MovieResult> movieResults = new ArrayList<>();
    private List<Cast> casts = new ArrayList<>();
    private List<Crew> crews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        movieDetailsVieModel = new ViewModelProvider(this).get(MovieDetailsVieModel.class);
        similarMoviesViewModel = new ViewModelProvider(this).get(SimilarMoviesViewModel.class);
        recommendedMoviesViewModel = new ViewModelProvider(this).get(RecommendedMoviesViewModel.class);
        movieCreditsViewModel = new ViewModelProvider(this).get(MovieCreditsViewModel.class);
        int movieId = getIntent().getIntExtra("movieId", -1);
        getMovieDetails(movieId);

        setSupportActionBar(activityMovieDetailsBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityMovieDetailsBinding.tabLayout.setupWithViewPager(activityMovieDetailsBinding.viewPager);
        activityMovieDetailsBinding.viewPager.setOffscreenPageLimit(3);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(new SimilarMoviesFragment(movieResults, movieId, 1), "MORE LIKE THIS");
        viewPagerAdapter.addFragment(new SimilarMoviesFragment(movieResults, movieId, 2), "RECOMMENDATIONS");
        viewPagerAdapter.addFragment(new CreditsFragment(crews, casts, movieId), "CAST & CREW");
        activityMovieDetailsBinding.viewPager.setAdapter(viewPagerAdapter);
    }


    void getMovieDetails(int movieId) {
        movieDetailsVieModel.getMovieDetails(movieId, getString(R.string.api_key)).observe(this, movieDetailResponse -> {
            activityMovieDetailsBinding.setMovie(movieDetailResponse);
        });
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}