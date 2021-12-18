package uz.tashxis.client.presentation.ui.bottom_nav


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
import uz.tashxis.client.R
import uz.tashxis.client.databinding.FragmentBottomNavBinding
import uz.tashxis.client.presentation.ui.activity.IFullScreen


class BottomNavFragment : Fragment(), IFullScreen {

    private var _binding: FragmentBottomNavBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val menuItems = setOf(
        R.id.boshFragment,
        R.id.navbatFragment,
        R.id.shifokorFragment,
        R.id.tashxisFragment,
        R.id.fragment
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

        appBarConfiguration = AppBarConfiguration(menuItems)
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.boshFragment || destination.id == R.id.tashxisFragment
                || destination.id == R.id.navbatFragment
            ) {
                binding.toolbar.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
            }

            when (destination.id) {
                R.id.boshFragment -> {
                }
                R.id.tashxisFragment -> {
                }
                R.id.shifokorFragment -> {
                }
                R.id.navbatFragment -> {
                }
                R.id.doctorsFragment -> {
                    destination.label = arguments?.getString("title")
                }
                R.id.aboutDoctorFragment -> {
                    destination.label = arguments?.getString("title")
                }
                R.id.stackFragment -> {
                }
            }
        }
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
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
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

    override fun setToolbarTitle(name: String?) {
        binding.toolbar.title = name
    }
}

interface NavigationOpenListener {
    fun onDrawerOpen()
}
