package uz.tashxis.client.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import uz.tashxis.client.R
import uz.tashxis.client.business.util.lazyFast
import uz.tashxis.client.databinding.ActivityMainBinding
import uz.tashxis.client.databinding.NavigationHeaderBinding
import uz.tashxis.client.presentation.ui.auth.preference.PrefHelper
import uz.tashxis.client.presentation.ui.bottom_nav.BottomNavFragment
import uz.tashxis.client.presentation.ui.bottom_nav.NavigationOpenListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val preferences by lazyFast { PrefHelper.getPref(this) }
    private val navBinding by lazyFast { NavigationHeaderBinding.inflate(layoutInflater) }
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
        checkLocationPermission()
    }

    private fun checkLocationPermission() {

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
            val navHeader = binding.navView.getHeaderView(0)
            Glide
                .with(uz.tashxis.client.App.context!!)
                .load(preferences.imageUri)
                .placeholder(R.drawable.ic_profile)
                .into(navHeader.findViewById<ShapeableImageView>(R.id.shiv_profile))
            navHeader.findViewById<MaterialTextView>(R.id.tv_fio).text =
                "${preferences.surename} ${preferences.name}"
            navHeader.findViewById<MaterialTextView>(R.id.tv_id).text = "${preferences.id}"
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            super.onBackPressed()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}