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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val text = binding.loginTextView.text
        if (!text.isNullOrBlank())
            outState.putString("text", text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val text = savedInstanceState.getString("text")
        if (!text.isNullOrBlank())
            binding.loginTextView.text = text
    }
}