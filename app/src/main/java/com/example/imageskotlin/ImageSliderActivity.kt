package com.example.imageskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.imageskotlin.model.ImagesModel

class ImageSliderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_slider)


        val list = intent?.getParcelableArrayListExtra<ImagesModel>("images_list")
            ?: throw IllegalStateException("Songs array list is null")

        Toast.makeText(this, list.size, Toast.LENGTH_SHORT).show()
    }
}