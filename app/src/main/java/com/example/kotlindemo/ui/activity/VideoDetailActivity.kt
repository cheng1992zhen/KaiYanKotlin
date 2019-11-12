package com.example.kotlindemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlindemo.R

class VideoDetailActivity : AppCompatActivity() {
    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }
}
