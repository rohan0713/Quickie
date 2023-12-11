package com.delivery.quickie.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delivery.quickie.data.food_items
import com.delivery.quickie.databinding.FoodItemBinding
import com.delivery.quickie.ui.activities.DetailsActivity
import com.squareup.picasso.Picasso

class FoodListAdapter(val list: List<food_items>) : RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

    lateinit var binding: FoodItemBinding
    inner class ViewHolder(itemview: FoodItemBinding) : RecyclerView.ViewHolder(itemview.root) {
        fun bind(foodItems: food_items) {
            binding.foodItemName.text = foodItems.strMeal.toString()
            val url = foodItems.strMealThumb.replace("\\\\", "")
            Picasso.get().load(url).into(binding.ivFood)

            itemView.setOnClickListener {
                Intent(binding.root.context, DetailsActivity::class.java).also {
                    it.putExtra("url", url)
                    it.putExtra("title", foodItems.strMeal)
                    binding.root.context.startActivity(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}