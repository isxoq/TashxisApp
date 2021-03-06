package com.example.tashxis.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tashxis.R
import com.example.tashxis.databinding.ActivityMainBinding
import com.example.tashxis.ui.bosh_oyna.BoshFragment
import com.example.tashxis.ui.navbat_oyna.NavbatFragment
import com.example.tashxis.ui.shifokor_oyna.ShifokorFragment
import com.example.tashxis.ui.tashxis_oyna.TashxisFragment

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