package com.example.tashxis.korinishlar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import com.example.tashxis.R
import com.example.tashxis.databinding.ActivityRegisterBinding
import com.example.tashxis.databinding.ActivitySlideBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTasdiqlash.setOnClickListener {
            if (binding.telRaqam.rawText.toString() == "995193150"){
                binding.raqamTerish.visibility = View.GONE
                binding.kodTerish.visibility = View.VISIBLE
                binding.tvSmsKod.setText("Biz +998" + binding.telRaqam.rawText + " telefon raqamingizga sms orqali faollashtirish kodini yubordik.")
            }
        }

        binding.smsKod.addTextChangedListener {
            if (binding.smsKod.rawText.toString() == "1122"){

            }
        }

    }
}