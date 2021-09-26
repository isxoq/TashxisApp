package com.example.tashxis.presentation.ui.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.tashxis.R
import com.example.tashxis.databinding.FragmentBottomNavBinding



class BottomNavFragment : Fragment() {

    private var _binding: FragmentBottomNavBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val menuItems = setOf(
        R.id.boshFragment,
        R.id.navbatFragment,
        R.id.shifokorFragment,
        R.id.tashxisFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        navController =
            (childFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment).navController
        binding.bottomNavView.setupWithNavController(navController)
//        appBarConfiguration = AppBarConfiguration(menuItems)
        binding.bottomNavView.setOnItemSelectedListener {
            if (NavigationUI.onNavDestinationSelected(it, navController)) {
                true
            } else {
                when (it.itemId) {
                    R.id.menu -> {
                        openDrawer()
                        true
                    }
                    else -> false
                }
            }
        }
    }


    private fun openDrawer() {
        listener?.onDrawerOpen()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        var listener: NavigationOpenListener? = null
    }
}

interface NavigationOpenListener {
    fun onDrawerOpen()
}
