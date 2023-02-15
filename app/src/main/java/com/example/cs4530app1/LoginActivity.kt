package com.example.cs4530app1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cs4530app1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstName: String? = intent.getStringExtra("firstName")
        val lastName: String? = intent.getStringExtra("lastName")

        binding.loginTextView.text = String.format(getString(R.string.login_message), firstName, lastName)
    }
}