package com.example.tashxis.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.tashxis.R
import com.example.tashxis.databinding.ActivityMainBinding
import com.example.tashxis.presentation.ui.bottom_nav.BottomNavFragment
import com.example.tashxis.presentation.ui.bottom_nav.NavigationOpenListener


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val menuItems = setOf(
        R.id.bottom_nav_fragment,
        R.id.id_profil_fragment,
        R.id.id_settings_fragment,
        R.id.id_share_fragment,
        R.id.id_aloqa_fragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(menuItems, binding.drawerLayout)
//        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        BottomNavFragment.listener = onDrawerOpenListener
    }

    private val onDrawerOpenListener = object : NavigationOpenListener {
        override fun onDrawerOpen() {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isOpen){
            binding.drawerLayout.close()
        }
        else{
            super.onBackPressed()
        }

    }
}