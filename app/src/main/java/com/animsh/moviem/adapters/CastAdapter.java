package com.animsh.moviem.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ItemCastContainerBinding;
import com.animsh.moviem.model.Cast;

import java.util.List;

/**
 * Created by animsh on 2/11/2021.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private List<Cast> casts;
    private LayoutInflater layoutInflater;

    public CastAdapter(List<Cast> casts) {
        this.casts = casts;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCastContainerBinding castBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_cast_container, parent, false
        );
        return new CastViewHolder(castBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.bindCast(casts.get(position));
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        private ItemCastContainerBinding itemCastContainerBinding;

        public CastViewHolder(ItemCastContainerBinding itemCastContainerBinding) {
            super(itemCastContainerBinding.getRoot());
            this.itemCastContainerBinding = itemCastContainerBinding;
        }

        public void bindCast(Cast cast) {
            itemCastContainerBinding.setCast(cast);
            itemCastContainerBinding.executePendingBindings();
        }
    }
}
