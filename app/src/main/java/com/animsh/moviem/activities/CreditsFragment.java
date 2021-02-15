package com.animsh.moviem.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.animsh.moviem.R;
import com.animsh.moviem.adapters.CastAdapter;
import com.animsh.moviem.adapters.CrewAdapter;
import com.animsh.moviem.databinding.FragmentCreditsBinding;
import com.animsh.moviem.model.Cast;
import com.animsh.moviem.model.Crew;
import com.animsh.moviem.viewmodels.moviesviewmodel.MovieCreditsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CreditsFragment extends Fragment {

    private FragmentCreditsBinding fragmentCreditsBinding;
    private CrewAdapter crewAdapter;
    private CastAdapter castAdapter;
    private MovieCreditsViewModel movieCreditsViewModel;
    private List<Crew> crews = new ArrayList<>();
    private List<Cast> casts = new ArrayList<>();
    private int movieId = -1;

    public CreditsFragment() {
        // Required empty public constructor
    }

    public CreditsFragment(List<Crew> crews, List<Cast> casts, int movieId) {
        this.crews = crews;
        this.casts = casts;
        this.movieId = movieId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieCreditsViewModel = new ViewModelProvider(this).get(MovieCreditsViewModel.class);
        castAdapter = new CastAdapter(casts);
        fragmentCreditsBinding.castRv.setHasFixedSize(true);
        fragmentCreditsBinding.castRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentCreditsBinding.castRv.setAdapter(castAdapter);

        crewAdapter = new CrewAdapter(crews);
        fragmentCreditsBinding.crewRv.setHasFixedSize(true);
        fragmentCreditsBinding.crewRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        fragmentCreditsBinding.crewRv.setAdapter(crewAdapter);

        getCredits();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCreditsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_credits, container, false);
        return fragmentCreditsBinding.getRoot();
    }

    public void getCredits() {
        movieCreditsViewModel.getMovieCredits(movieId, getString(R.string.api_key)).observe(getViewLifecycleOwner(), moviesCreditsResponse -> {
            if (moviesCreditsResponse != null) {
                if (!moviesCreditsResponse.getCast().isEmpty() || !moviesCreditsResponse.getCrew().isEmpty()) {
                    casts.addAll(moviesCreditsResponse.getCast());
                    castAdapter.notifyDataSetChanged();

                    crews.addAll(moviesCreditsResponse.getCrew());
                    crewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}