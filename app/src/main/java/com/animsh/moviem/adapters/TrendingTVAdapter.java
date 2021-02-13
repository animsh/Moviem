package com.animsh.moviem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ItemTrendingTvContainerBinding;
import com.animsh.moviem.listeners.TvListener;
import com.animsh.moviem.response.tvshowzresponse.TVShowResult;

import java.util.List;

/**
 * Created by animsh on 2/11/2021.
 */
public class TrendingTVAdapter extends RecyclerView.Adapter<TrendingTVAdapter.TVViewHolder> {

    private List<TVShowResult> tvShows;
    private LayoutInflater layoutInflater;
    private TvListener tvListener;

    public TrendingTVAdapter(List<TVShowResult> tvShows, TvListener tvListener) {
        this.tvShows = tvShows;
        this.tvListener = tvListener;
    }

    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemTrendingTvContainerBinding tvBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_trending_tv_container, parent, false
        );
        return new TVViewHolder(tvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVViewHolder holder, int position) {
        holder.bindTV(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TVViewHolder extends RecyclerView.ViewHolder {

        private ItemTrendingTvContainerBinding itemTrendingTvContainerBinding;

        public TVViewHolder(ItemTrendingTvContainerBinding itemTrendingTvContainerBinding) {
            super(itemTrendingTvContainerBinding.getRoot());
            this.itemTrendingTvContainerBinding = itemTrendingTvContainerBinding;
        }

        public void bindTV(TVShowResult tvShowResult) {
            itemTrendingTvContainerBinding.setTV(tvShowResult);
            itemTrendingTvContainerBinding.executePendingBindings();
            itemTrendingTvContainerBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvListener.onTVClicked(tvShowResult);
                }
            });
        }
    }
}
