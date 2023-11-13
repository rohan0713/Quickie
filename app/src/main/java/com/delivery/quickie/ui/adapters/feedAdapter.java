package com.delivery.quickie.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.quickie.R;
import com.delivery.quickie.data.PostsItem;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class feedAdapter extends RecyclerView.Adapter<feedAdapter.Viewholder> {
    List<PostsItem> list;
    public feedAdapter(List<PostsItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(PostsItem postsItem) {
            TextView username = itemView.findViewById(R.id.tvUsername);
            TextView location = itemView.findViewById(R.id.location);
            TextView likes = itemView.findViewById(R.id.tvLikes);
            TextView caption = itemView.findViewById(R.id.tvCaption);
            ImageView post = itemView.findViewById(R.id.image);

            if(postsItem.getPostImg() != null) {
                Picasso.get().load(postsItem.getPostImg()).into(post);
            }
            username.setText(postsItem.getUsername());
            location.setText(postsItem.getLocation());
            likes.setText(String.valueOf(postsItem.getLikes()) + " likes");
            caption.setText(postsItem.getCaption());
        }
    }
}
