package com.animsh.moviem.response;

import java.util.List;

/**
 * Created by animsh on 2/10/2021.
 */
public class UniqueMovieResponse {
    private String page;
    private List<MovieResult> results;
    private MovieDates dates;
    private int total_pages;
    private int total_results;

    public UniqueMovieResponse() {
    }

    public UniqueMovieResponse(String page, List<MovieResult> results, MovieDates dates, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.dates = dates;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<MovieResult> getResults() {
        return results;
    }

    public void setResults(List<MovieResult> results) {
        this.results = results;
    }

    public MovieDates getDates() {
        return dates;
    }

    public void setDates(MovieDates dates) {
        this.dates = dates;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    @Override
    public String toString() {
        return "UniqueMovieResponse{" +
                "page='" + page + '\'' +
                ", dates=" + dates +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }
}
