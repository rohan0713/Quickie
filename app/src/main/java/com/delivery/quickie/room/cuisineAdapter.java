package com.delivery.quickie.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.quickie.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class cuisineAdapter extends RecyclerView.Adapter<cuisineAdapter.Viewmodel> {

    List<cuisine> list;
    public cuisineAdapter(List<cuisine> list){
        this.list = list;
    }

    @NonNull
    @Override
    public cuisineAdapter.Viewmodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine, parent, false);
        return new Viewmodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cuisineAdapter.Viewmodel holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size()/2;
    }

    public static class Viewmodel extends RecyclerView.ViewHolder {
        public Viewmodel(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(cuisine cuisine) {

            MaterialButton button = itemView.findViewById(R.id.cuisine);
            button.setText(cuisine.strArea);
        }
    }
}
