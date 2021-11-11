package com.example.tashxis.presentation.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.tashxis.R
import com.example.tashxis.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var navController: NavController

    private val menuItems = setOf(
        R.id.loginFragment,
        R.id.otpFragment,
        R.id.royxatdanOtishFragment,
        R.id.registerFragment
    )
    private val appBarConfiguration = AppBarConfiguration(menuItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.fragment)
        binding.authToolbar.setupWithNavController(navController, appBarConfiguration)
        binding.authToolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

}
