package com.animsh.moviem.listeners;

import com.animsh.moviem.response.moviesresponse.MovieResult;

/**
 * Created by animsh on 2/13/2021.
 */
public interface MovieListener {
    void onMovieClicked(MovieResult movie);
}
