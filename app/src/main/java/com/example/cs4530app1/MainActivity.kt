package com.example.cs4530app1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cs4530app1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraButton.setOnClickListener { onClickPictureButton() }
    }

    private fun onClickPictureButton() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            getPicture.launch(cameraIntent)
        } catch (e: ActivityNotFoundException) {
            print(e)
        }
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
            }
        }
}