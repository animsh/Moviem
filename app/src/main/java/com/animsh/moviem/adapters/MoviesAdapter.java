package com.animsh.moviem.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ItemMovieContainerBinding;
import com.animsh.moviem.listeners.MovieListener;
import com.animsh.moviem.response.moviesresponse.MovieResult;

import java.util.List;

/**
 * Created by animsh on 2/11/2021.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<MovieResult> movies;
    private LayoutInflater layoutInflater;
    private MovieListener movieListener;

    public MoviesAdapter(List<MovieResult> movies, MovieListener movieListener) {
        this.movies = movies;
        this.movieListener = movieListener;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemMovieContainerBinding movieBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_movie_container, parent, false
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

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        private ItemMovieContainerBinding itemMovieContainerBinding;

        public MoviesViewHolder(ItemMovieContainerBinding itemMovieContainerBinding) {
            super(itemMovieContainerBinding.getRoot());
            this.itemMovieContainerBinding = itemMovieContainerBinding;
        }

        public void bindMovie(MovieResult movie) {
            itemMovieContainerBinding.setMovie(movie);
            itemMovieContainerBinding.executePendingBindings();
            itemMovieContainerBinding.getRoot().setOnClickListener(view -> movieListener.onMovieClicked(movie));
        }
    }
}
