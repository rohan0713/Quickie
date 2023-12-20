package com.delivery.quickie.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityCartBinding
import com.delivery.quickie.room.foodViewModel
import com.delivery.quickie.ui.adapters.CartAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        viewModel.totalAmount.observe(this) {
            it?.let {
                binding.tvTotalAmount.text = "₹ ${it.toString()}"
            }
        }

        binding.btnCheckout.setOnClickListener {
            Intent(this@CartActivity, ConfirmationActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnApply.setOnClickListener {

            if(binding.etCode.text != null){

                val code = binding.etCode.text.toString()
                val validCode = "quick10"

                if(code.equals(validCode, ignoreCase = true)){
                    binding.tvDiscount.text = "10% Discount applied"
                    binding.tvDiscount.setTextColor(resources.getColor(R.color.base))
                    binding.tvDiscount.visibility = View.VISIBLE

                    viewModel.totalAmount.observe(this) {
                        it?.let {
                            val amount = it.minus(it * 0.1)
                            binding.tvTotalAmount.text = "₹ ${amount.toString()}"
                        }
                    }

                }else{
                    binding.tvDiscount.text = "invalid code"
                    binding.tvDiscount.setTextColor(resources.getColor(R.color.invalid))
                    binding.tvDiscount.visibility = View.VISIBLE
                }
            }
        }
    }
}