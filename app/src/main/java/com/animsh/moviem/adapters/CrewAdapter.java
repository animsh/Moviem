package com.animsh.moviem.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.animsh.moviem.R;
import com.animsh.moviem.databinding.ItemCrewContainerBinding;
import com.animsh.moviem.model.Crew;

import java.util.List;

/**
 * Created by animsh on 2/11/2021.
 */
public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private List<Crew> crews;
    private LayoutInflater layoutInflater;

    public CrewAdapter(List<Crew> crews) {
        this.crews = crews;
    }


    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCrewContainerBinding crewBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_crew_container, parent, false
        );
        return new CrewViewHolder(crewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        holder.bindCrew(crews.get(position));
    }

    @Override
    public int getItemCount() {
        return crews.size();
    }

    class CrewViewHolder extends RecyclerView.ViewHolder {

        private ItemCrewContainerBinding itemCrewContainerBinding;

        public CrewViewHolder(ItemCrewContainerBinding itemCrewContainerBinding) {
            super(itemCrewContainerBinding.getRoot());
            this.itemCrewContainerBinding = itemCrewContainerBinding;
        }

        public void bindCrew(Crew crew) {
            itemCrewContainerBinding.setCrew(crew);
            itemCrewContainerBinding.executePendingBindings();
        }
    }
}
