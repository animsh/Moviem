package com.animsh.moviem.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.animsh.moviem.R;
import com.animsh.moviem.ui.home.MovieFragment;
import com.animsh.moviem.ui.home.MyListFragment;
import com.animsh.moviem.ui.home.TvFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = view.findViewById(R.id.newsTab);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(requireContext())
                .add("Movies", MovieFragment.class)
                .add("Tv Shows", TvFragment.class)
                .add("My List", MyListFragment.class)
                .create());
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}