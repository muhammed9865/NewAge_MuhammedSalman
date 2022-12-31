package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.calculator

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.muhammed.muhammedsalmannewage.BuildConfig
import com.muhammed.muhammedsalmannewage.R
import com.muhammed.muhammedsalmannewage.databinding.FragmentCalculatorBinding
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIResult
import com.muhammed.muhammedsalmannewage.presentation.activity.bmi.MainViewModel
import com.muhammed.muhammedsalmannewage.presentation.common.fragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CalculatorFragment : ViewBindingFragment<FragmentCalculatorBinding>() {

    private val viewModel by viewModels<CalculatorViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var interstitialAd: InterstitialAd

    override fun initBinding(): FragmentCalculatorBinding {
        return FragmentCalculatorBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadAd()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateChange()

        val transition = Slide(Gravity.START)
        enterTransition = transition
        exitTransition = transition

        // Handling Buttons clicks
        with(binding) {
            calculateBtn.setOnClickListener {
                viewModel.calculate()
            }
        }
    }

    private fun observeStateChange() {
        viewModel.state.onEach { state ->
            with(state) {
                // TODO 1- Propagate RecyclerViews adapter with State lists

                if (showAd)
                    showAd()

                bmiResult?.let { result ->
                    onBMIResultAvailable(result)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun showAd() {
        if (this::interstitialAd.isInitialized) {
            interstitialAd.setImmersiveMode(true)
            interstitialAd.show(requireActivity())
        }
    }

    private fun loadAd() {
        val request = AdRequest.Builder().build()
        val interstitialAdID = BuildConfig.InterstitialAd_ID
        InterstitialAd.load(
            requireContext(),
            interstitialAdID,
            request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    interstitialAd = ad
                }
            }
        )
    }

    private fun onBMIResultAvailable(bmiResult: BMIResult) {
        mainViewModel.bmiResult = bmiResult
        viewModel.afterBMIResultsPublished()
        findNavController().navigate(R.id.resultFragment)
    }

    private fun setUpRecyclerViews() {

    }
}