package com.delivery.quickie.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.delivery.quickie.R
import com.delivery.quickie.databinding.ActivityLoginBinding
import com.delivery.quickie.network.RetrofitClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    private var upload : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityLoginBinding.inflate(layoutInflater).also { binding = it }.root)

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        upload = sharedPreferences.getBoolean("upload", false)

        binding.btnLogin.setOnClickListener {
            validateCredentials()
        }

        binding.tvSignUp.setOnClickListener {
            Intent(this@LoginActivity, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun validateCredentials() {

        val email = binding.etEmail.text
        val password = binding.etPassword.text

        if (email != null && password != null) {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = RetrofitClient.dbApi.login(email.toString(), password.toString())
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        val flag = response.body().let {
                            it?.status
                        }
                        if (flag == true) {
                            val editor = sharedPreferences.edit()
                            editor.putBoolean("access", true)
                            editor.putString("email", email.toString())
                            editor.apply()

                            if (upload) {
                                Intent(this@LoginActivity, home::class.java).also {
                                    startActivity(it)
                                    finish()
                                }
                            }else{
                                Intent(this@LoginActivity, ProfileActivity::class.java).also {
                                    startActivity(it)
                                    finish()
                                }
                            }
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Invalid Credentials",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

}