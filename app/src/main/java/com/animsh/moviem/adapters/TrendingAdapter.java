package com.animsh.moviem.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ItemTrendingMovieContainerBinding;
import com.animsh.moviem.response.moviesresponse.MovieResult;

import java.util.List;

/**
 * Created by animsh on 2/11/2021.
 */
public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MoviesViewHolder> {

    private List<MovieResult> movies;
    private LayoutInflater layoutInflater;

    public TrendingAdapter(List<MovieResult> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemTrendingMovieContainerBinding movieBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_trending_movie_container, parent, false
        );
        return new MoviesViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bindMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        private ItemTrendingMovieContainerBinding itemTrendingMovieContainerBinding;

        public MoviesViewHolder(ItemTrendingMovieContainerBinding itemTrendingMovieContainerBinding) {
            super(itemTrendingMovieContainerBinding.getRoot());
            this.itemTrendingMovieContainerBinding = itemTrendingMovieContainerBinding;
        }

        public void bindMovie(MovieResult movie) {
            itemTrendingMovieContainerBinding.setMovie(movie);
            itemTrendingMovieContainerBinding.executePendingBindings();
        }
    }
}