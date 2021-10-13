package com.example.tashxis.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tashxis.databinding.ActivitySplashBinding
import com.example.tashxis.presentation.ui.auth.RegisterActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView3.postDelayed({
            startActivity(Intent(this , RegisterActivity::class.java))
            finish()
        },50)

    }
}