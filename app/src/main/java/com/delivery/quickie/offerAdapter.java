package com.delivery.quickie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class offerAdapter extends RecyclerView.Adapter<offerAdapter.ViewModel> {

    public offerAdapter(){

    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_item, parent, false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        public ViewModel(@NonNull View itemView) {
            super(itemView);
        }
    }
}
