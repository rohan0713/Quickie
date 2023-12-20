package com.delivery.quickie.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivitySignUpBinding
import com.delivery.quickie.network.RetrofitClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySignUpBinding.inflate(layoutInflater).also { binding = it }.root)

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        binding.tvSignIn.setOnClickListener {
            Intent(this@SignUpActivity, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
        binding.btnSignUp.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            validateCredentials();
        }

    }

    private fun validateCredentials() {

        val email = binding.etEmail.text
        val password = binding.etPassword.text

        if (email != null && password != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = RetrofitClient.dbApi.createUser(email.toString(), password.toString())
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE;
                        val flag = response.body().let {
                            it?.status
                        }
                        if (flag == true) {
                            Toast.makeText(
                                binding.root.context,
                                response.body()?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                binding.root.context,
                                response.body()?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

}