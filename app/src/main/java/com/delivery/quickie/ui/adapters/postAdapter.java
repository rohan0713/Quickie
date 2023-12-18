package com.delivery.quickie.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.quickie.R;
import com.delivery.quickie.network.ProfileResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class postAdapter extends RecyclerView.Adapter<postAdapter.ViewHolder> {

    List<String> profile;
    public postAdapter(List<String> body){
        this.profile = body;
    }

    @NonNull
    @Override
    public postAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postAdapter.ViewHolder holder, int position) {
        holder.bind(profile.get(position));
    }

    @Override
    public int getItemCount() {
        return profile.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(String s) {
            ImageView post = itemView.findViewById(R.id.ivPosts);
            Picasso.get().load("https://quickie-backend.vercel.app/api/images/" + s).into(post);
        }
    }
}
