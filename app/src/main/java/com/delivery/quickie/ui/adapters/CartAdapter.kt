package com.delivery.quickie.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delivery.quickie.data.CartItems
import com.delivery.quickie.databinding.CartItemBinding
import com.delivery.quickie.room.foodViewModel

class CartAdapter(
    val cartItems: List<CartItems>,
val viewmodel: foodViewModel
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    lateinit var binding: CartItemBinding
    inner class ViewHolder(itemView: CartItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bind(cartItems: CartItems) {
            binding.tvFoodName.text = cartItems.foodName
            binding.tvQuantity.text = cartItems.quantity.toString()
            binding.tvAmount.text = "₹ ${cartItems.amount * cartItems.quantity}"

            binding.tvDecreaseCount.setOnClickListener {
                viewmodel.remove(cartItems.foodName)
                viewmodel.deleteZeros()
            }

            binding.tvIncreaseCount.setOnClickListener {
                viewmodel.add(cartItems.foodName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }
}