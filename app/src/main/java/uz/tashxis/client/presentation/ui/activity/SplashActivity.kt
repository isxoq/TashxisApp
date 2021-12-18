package uz.tashxis.client.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import uz.tashxis.client.databinding.ActivitySplashBinding
import uz.tashxis.client.presentation.ui.auth.preference.PrefHelper

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val preferences by lazy { PrefHelper.getPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(mainLooper).postDelayed({
            if (preferences.token == "" || preferences.name == null) {
                if (isFirstTimeAppStart()) {
                    startActivity(Intent(this, SlideActivity::class.java))

                } else {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }

            } else {
                startActivity(Intent(this, MainActivity::class.java))
                //TODO
            }
            finish()
        }, 200)

    }

    private fun isFirstTimeAppStart(): Boolean {
        val pref = applicationContext.getSharedPreferences("SLIDER_APP", Context.MODE_PRIVATE)
        Log.d("TAG", "isFirstTimeAppStart: ${pref.getBoolean("APP_START", true)} ")
        return pref.getBoolean("APP_START", true)
    }

}