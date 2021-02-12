package com.animsh.moviem.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ItemTvShowContainerBinding;
import com.animsh.moviem.response.tvshowzresponse.TVShowResult;

import java.util.List;

/**
 * Created by animsh on 2/11/2021.
 */
public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVViewHolder> {

    private List<TVShowResult> tvShowResults;
    private LayoutInflater layoutInflater;

    public TVAdapter(List<TVShowResult> tvShowResults) {
        this.tvShowResults = tvShowResults;
    }

    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemTvShowContainerBinding tvBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_tv_show_container, parent, false
        );
        return new TVViewHolder(tvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVViewHolder holder, int position) {
        holder.bindTV(tvShowResults.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowResults.size();
    }

    static class TVViewHolder extends RecyclerView.ViewHolder {

        private ItemTvShowContainerBinding itemTvShowContainerBinding;

        public TVViewHolder(ItemTvShowContainerBinding itemTvShowContainerBinding) {
            super(itemTvShowContainerBinding.getRoot());
            this.itemTvShowContainerBinding = itemTvShowContainerBinding;
        }

        public void bindTV(TVShowResult tv) {
            itemTvShowContainerBinding.setTVShow(tv);
            itemTvShowContainerBinding.executePendingBindings();
        }
    }
}
