package com.delivery.quickie.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.delivery.quickie.R
import com.delivery.quickie.data.CartItems
import com.delivery.quickie.databinding.ActivityDetailsBinding
import com.delivery.quickie.room.Repository
import com.delivery.quickie.room.foodViewModel
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    private lateinit var viewmodel: foodViewModel
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityDetailsBinding.inflate(layoutInflater).also { binding = it }.root)

        window.statusBarColor = Color.WHITE

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        binding.tvTitle.text = title.toString()
        Picasso.get().load(url).placeholder(R.color.borderColor).into(binding.ivFood)

        repository = Repository(application)
        viewmodel = ViewModelProvider(this).get(foodViewModel::class.java)
//        viewmodel.cartItems.observe(this, Observer {
//
//        })


        binding.btnViewCart.setOnClickListener {
            Intent(this@DetailsActivity, CartActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.tvIncreaseCount.setOnClickListener {
            updateCountWithSlideAnimation(1)
            val list = viewmodel.findItem(title).value
            Log.d("invoked", list.toString())

            if (list != null) {
                    if (list.size > 0) {
                        viewmodel.add(title)
                        Log.d("invoked", "add function")
                    }
                } else {
                    viewmodel.insertIntoCart(
                        CartItems(
                            title!!,
                            binding.tvCount.text.toString().toInt(),
                            "200".toInt()
                        )
                    )
                    Log.d("invoked", "insert function")
                }
        }

        binding.tvDecreaseCount.setOnClickListener {

            binding.tvCount.text = viewmodel.getQuantity(title).toString()

            if (binding.tvCount.text.toString().toInt() > 0) {
                updateCountWithSlideAnimation(-1)
                viewmodel.remove(title.toString())
                viewmodel.deleteZeros()
            }
        }

    }

    private fun updateCountWithSlideAnimation(delta: Int) {
        val currentCount = binding.tvCount.text.toString().toInt()
        val newCount = currentCount + delta

        val moveAnimation = if (delta > 0) {
            // For increase, move up
            ObjectAnimator.ofFloat(binding.tvCount, View.TRANSLATION_Y, 0f, -50f)
        } else {
            // For decrease, move down
            ObjectAnimator.ofFloat(binding.tvCount, View.TRANSLATION_Y, 0f, 50f)
        }
        moveAnimation.duration = 300 // Adjust the duration as needed

        val fadeOut = ObjectAnimator.ofFloat(binding.tvCount, View.ALPHA, 1f, 0f)
        fadeOut.duration = 150 // Adjust the duration as needed

        val moveBackAnimation = if (delta > 0) {
            // For increase, move down
            ObjectAnimator.ofFloat(binding.tvCount, View.TRANSLATION_Y, -50f, 0f)
        } else {
            // For decrease, move up
            ObjectAnimator.ofFloat(binding.tvCount, View.TRANSLATION_Y, 50f, 0f)
        }
        moveBackAnimation.duration = 300 // Adjust the duration as needed

        val fadeIn = ObjectAnimator.ofFloat(binding.tvCount, View.ALPHA, 0f, 1f)
        fadeIn.duration = 150 // Adjust the duration as needed

        moveAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                binding.tvCount.text = newCount.toString()

                fadeOut.start()
                moveBackAnimation.start()
                fadeIn.start()
            }
        })

        moveAnimation.start()
    }
}