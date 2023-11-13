package com.delivery.quickie.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.quickie.R;

import java.util.List;

public class offerAdapter extends RecyclerView.Adapter<offerAdapter.ViewModel> {
    List<Integer> list;
    public offerAdapter(List<Integer> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_item, parent, false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        public ViewModel(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Integer integer) {
            ImageView ivOffer = itemView.findViewById(R.id.offer_image);
            ivOffer.setBackgroundResource(integer);
        }
    }
}
