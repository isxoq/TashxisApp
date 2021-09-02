package com.example.tashxis.korinishlar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tashxis.R
import com.example.tashxis.databinding.ActivityMainBinding
import com.example.tashxis.korinishlar.bosh_oyna.BoshFragment
import com.example.tashxis.korinishlar.navbat_oyna.NavbatFragment
import com.example.tashxis.korinishlar.shifokor_oyna.ShifokorFragment
import com.example.tashxis.korinishlar.tashxis_oyna.TashxisFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val boshFragment = BoshFragment.newInstance()
    val navbatFragment = NavbatFragment.newInstance()
    val shifokorFragment = ShifokorFragment.newInstance()
    val tashxisFragment = TashxisFragment.newInstance()

    var activeFragment: Fragment = boshFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.frame_layout , boshFragment , boshFragment.tag).hide(boshFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout , navbatFragment , navbatFragment.tag).hide(navbatFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout , shifokorFragment , shifokorFragment.tag).hide(shifokorFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout , tashxisFragment , tashxisFragment.tag).hide(tashxisFragment).commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        binding.bottomNavView.setOnNavigationItemSelectedListener {

            if (it.itemId == R.id.bosh_menu){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(boshFragment).commit()
                activeFragment = boshFragment
            }else if (it.itemId == R.id.navbat_menu){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(navbatFragment).commit()
                activeFragment = navbatFragment
            }else if (it.itemId == R.id.shifokor_menu){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(shifokorFragment).commit()
                activeFragment = shifokorFragment
            }else if (it.itemId == R.id.tashxis_menu){
                supportFragmentManager.beginTransaction().hide(activeFragment).show(tashxisFragment).commit()
                activeFragment = tashxisFragment
            }else if (it.itemId == R.id.menu){
                binding.drawerLayout.open()
            }

            return@setOnNavigationItemSelectedListener true
        }


    }

}