package com.example.gallerysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.MemoryCategory
import com.example.gallerysample.utilities.GlideApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlideApp.get(applicationContext).setMemoryCategory(MemoryCategory.LOW)
    }
}