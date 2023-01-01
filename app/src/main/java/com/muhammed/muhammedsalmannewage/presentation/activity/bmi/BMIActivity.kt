package com.muhammed.muhammedsalmannewage.presentation.activity.bmi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.muhammed.muhammedsalmannewage.R
import com.muhammed.muhammedsalmannewage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BMIActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MuhammedSalmanNewAge)
        setContentView(binding.root)
        setupToolbarWithNavigation()
        navController.addOnDestinationChangedListener(this)
    }


    private fun setupToolbarWithNavigation() {
        val toolbar = binding.bmiToolbar
        NavigationUI.setupWithNavController(
            toolbar,
            navController
        )
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?,
    ) {
        when(destination.id) {
            R.id.resultFragment -> {
                binding.bmiToolbar.setNavigationIcon(R.drawable.ic_back_arrow)

            }
        }
    }
}