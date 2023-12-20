package com.delivery.quickie.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {

    lateinit var binding: ActivityConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityConfirmationBinding.inflate(layoutInflater).also { binding = it }.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}