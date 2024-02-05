package com.pinslog.samples.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pinslog.samples.databinding.ActivityMainBinding

private const val TAG = "MainActivity2222"
class DrawingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}