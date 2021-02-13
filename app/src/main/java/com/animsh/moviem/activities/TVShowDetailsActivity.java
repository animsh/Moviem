package com.animsh.moviem.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ActivityTvShowDetailsBinding;
import com.animsh.moviem.viewmodels.tvshowsviewmodel.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_show_details);
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        int tvId = getIntent().getIntExtra("tvId", -1);
        getTVShowDetails(tvId);
    }

    public void getTVShowDetails(int tvId) {
        tvShowDetailsViewModel.getTVShowDetails(tvId, getString(R.string.api_key)).observe(this, tvShowDetails -> {
            Toast.makeText(this, "Name: " + tvShowDetails.getName(), Toast.LENGTH_SHORT).show();
        });
    }
}