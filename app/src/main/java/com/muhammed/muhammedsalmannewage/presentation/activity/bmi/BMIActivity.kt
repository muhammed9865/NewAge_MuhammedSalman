package com.muhammed.muhammedsalmannewage.presentation.activity.bmi

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.muhammed.muhammedsalmannewage.R
import com.muhammed.muhammedsalmannewage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        setContentView(binding.root)
        showSplashFor(SPLASH_DISPLAY_TIME)
        setupToolbarWithNavigation()
        navController.addOnDestinationChangedListener(this)
    }


    private fun showSplashFor(milli: Long) {
        with(binding) {
            lifecycleScope.launch {
                // Display the splash drawable manually if Build version is below Android 12
                // Android 12+ displays a customizable splash screen.
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    toggleSplashVisibility(show = true)
                    delay(milli)
                    toggleSplashVisibility(show = false) {
                        navHostFragmentContainer.visibility = View.VISIBLE
                    }
                } else {
                    navHostFragmentContainer.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun toggleSplashVisibility(show: Boolean, onFinish: () -> Unit = {}) {
        val scale = if (show) 1f else 0f
        val alpha = if (show) 1f else 0f
        binding.splashImage.animate()
            .scaleX(scale)
            .scaleY(scale)
            .alpha(alpha)
            .setDuration(500)
            .withEndAction(onFinish)
            .start()
    }


    private fun setupToolbarWithNavigation() {
        val toolbar = binding.bmiToolbar
        NavigationUI.setupWithNavController(
            toolbar,
            navController
        )
    }


    companion object {
        private const val SPLASH_DISPLAY_TIME = 1000L
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