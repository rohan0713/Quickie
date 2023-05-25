package com.delivery.quickie;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.quickie.room.food_items;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

        }
    }

    public void getList(List<food_items> food_items){
        this.list = food_items;
    }
}
