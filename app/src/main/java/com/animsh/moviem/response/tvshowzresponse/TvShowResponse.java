package com.animsh.moviem.response.tvshowzresponse;

import java.util.List;

/**
 * Created by animsh on 2/12/2021.
 */
public class TvShowResponse {
    private int page;
    private List<TVShowResult> results;
    private int total_results;
    private int total_pages;

    public TvShowResponse() {
    }

    public TvShowResponse(int page, List<TVShowResult> results, int total_results, int total_pages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TVShowResult> getResults() {
        return results;
    }

    public void setResults(List<TVShowResult> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
