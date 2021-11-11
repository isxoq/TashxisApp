package com.example.tashxis.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tashxis.databinding.ActivitySplashBinding
import com.example.tashxis.presentation.ui.auth.RegisterActivity
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var preferences: TashxisPrefs? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = PrefHelper.getPref(this)

        binding.textView3.postDelayed({
            if (preferences?.token == "") {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            if (preferences?.token != "") {
                //startActivity(Intent(this, MainActivity::class.java))
                startActivity(Intent(this, RegisterActivity::class.java))
                //TODO
            }

            finish()
        }, 60)

    }
}