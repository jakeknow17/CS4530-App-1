package com.example.cs4530app1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.cs4530app1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraButton.setOnClickListener { onClickPictureButton() }
        binding.submitButton.setOnClickListener { onClickSubmitButton() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (binding.cameraImageView.drawable != null) outState.putParcelable(
            "image", binding.cameraImageView.drawable.toBitmap()
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val image: Bitmap? = if (Build.VERSION.SDK_INT >= 33) savedInstanceState.getParcelable(
            "image", Bitmap::class.java
        )
        else @Suppress("DEPRECATION") savedInstanceState.getParcelable("image")

        if (image != null) {
            binding.cameraImageView.setImageBitmap(image)
            binding.thumbnailTextView.text = getString(R.string.thumbnail)
        }
    }

    private fun onClickPictureButton() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            getPicture.launch(cameraIntent)
        } catch (e: ActivityNotFoundException) {
            Log.e("MainActivity", e.toString())
        }
    }

    private fun onClickSubmitButton() {
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()

        if (firstName.isBlank()) {
            Toast.makeText(
                this, "Please enter your first name.", Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (lastName.isBlank()) {
            Toast.makeText(
                this, "Please enter your last name.", Toast.LENGTH_SHORT
            ).show()
            return
        }

        val loginIntent = Intent(this, LoginActivity::class.java)
        loginIntent.putExtra("firstName", firstName.trim())
        loginIntent.putExtra("lastName", lastName.trim())
        this.startActivity(loginIntent)
    }

    private val getPicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (Build.VERSION.SDK_INT >= 33) {
                    val image = it.data?.getParcelableExtra("data", Bitmap::class.java)
                    binding.cameraImageView.setImageBitmap(image)
                } else {
                    @Suppress("DEPRECATION") val image = it.data?.getParcelableExtra<Bitmap>("data")
                    binding.cameraImageView.setImageBitmap(image)
                }
                binding.thumbnailTextView.text = getString(R.string.thumbnail)
            }
        }
}