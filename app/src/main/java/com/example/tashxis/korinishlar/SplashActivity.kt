package com.example.tashxis.korinishlar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tashxis.R
import com.example.tashxis.databinding.ActivityMainBinding
import com.example.tashxis.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView3.postDelayed({
            startActivity(Intent(this , SlideActivity::class.java))
            finish()
        },3000)

    }
}