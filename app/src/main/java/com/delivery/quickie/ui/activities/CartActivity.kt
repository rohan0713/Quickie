package com.delivery.quickie.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityCartBinding
import com.delivery.quickie.room.foodViewModel
import com.delivery.quickie.ui.adapters.CartAdapter

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    lateinit var viewModel: foodViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityCartBinding.inflate(layoutInflater).also { binding = it }.root)

        binding.rvServices.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(foodViewModel::class.java)
        viewModel.cartItems.observe(this) {
            if (it.isNotEmpty()) {
                binding.rvServices.visibility = View.VISIBLE
                binding.tvItems.visibility = View.GONE
                binding.rvServices.adapter = CartAdapter(it, viewModel)
            }else{
                binding.rvServices.visibility = View.GONE
                binding.tvItems.visibility = View.VISIBLE
            }
        }

        binding.layoutBookMore.setOnClickListener {
            Intent(this@CartActivity, home::class.java).also {
                startActivity(it)
                finish()
            }
            finish()
        }
    }
}