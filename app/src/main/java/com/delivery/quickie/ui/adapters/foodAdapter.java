package com.delivery.quickie.ui.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.quickie.R;
import com.delivery.quickie.data.food_items;
import com.delivery.quickie.ui.activities.CartActivity;
import com.delivery.quickie.ui.activities.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class foodAdapter extends RecyclerView.Adapter<foodAdapter.Viewholder> {

    List<food_items> list;
    public foodAdapter(List<food_items> list){
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_category, parent, false);
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

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(food_items food_items) {
            ImageView food_image = itemView.findViewById(R.id.food_item);
            TextView food_item = itemView.findViewById(R.id.food_item_name);

            food_item.setText(food_items.strMeal);
            String url = food_items.strMealThumb.replaceAll("\\\\", "");
            Log.d("url", url);
            Picasso.get().load(url).placeholder(R.drawable.burger).fit().into(food_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(v.getContext(), DetailsActivity.class);
                    i.putExtra("url", url);
                    i.putExtra("title", food_items.strMeal);
                    v.getContext().startActivity(i);
                }
            });

        }
    }

    public void getList(List<food_items> food_items){
        this.list = food_items;
    }
}
