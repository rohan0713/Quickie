package com.delivery.quickie.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityDetailsBinding.inflate(layoutInflater).also { binding = it }.root)

        window.statusBarColor = Color.WHITE

        val url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")

        binding.tvTitle.text = title.toString()
        Picasso.get().load(url).placeholder(R.color.borderColor).into(binding.ivFood)

        binding.btnViewCart.setOnClickListener {
            Intent(this@DetailsActivity, CartActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}