package com.delivery.quickie.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.delivery.quickie.data.response
import com.delivery.quickie.databinding.ActivityFoodListBinding
import com.delivery.quickie.network.Retrofit
import com.delivery.quickie.ui.adapters.FoodListAdapter
import com.delivery.quickie.ui.adapters.foodAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodListActivity : AppCompatActivity() {

    lateinit var binding: ActivityFoodListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityFoodListBinding.inflate(layoutInflater).also { binding = it }.root)

        val cuisine = intent.getStringExtra("cuisine")

        binding.rvMeals.layoutManager = LinearLayoutManager(this)

        val res = Retrofit.services.meals(cuisine)
        res.enqueue(object : Callback<response> {
            override fun onResponse(call: Call<response>, response: Response<response>) {
                if (response.isSuccessful) {
                    val list = response.body()?.meals?.toList()
                    binding.rvMeals.adapter = FoodListAdapter(list!!)
                } else {
                    Toast.makeText(this@FoodListActivity, "failed", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<response>, t: Throwable) {
                Toast.makeText(this@FoodListActivity, "No Internet", Toast.LENGTH_LONG).show()
            }

        })

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}