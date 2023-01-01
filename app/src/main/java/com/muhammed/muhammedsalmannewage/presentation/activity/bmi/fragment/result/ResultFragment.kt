package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.result

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.transition.Slide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.muhammed.muhammedsalmannewage.BuildConfig
import com.muhammed.muhammedsalmannewage.databinding.FragmentResultBinding
import com.muhammed.muhammedsalmannewage.presentation.activity.bmi.MainViewModel
import com.muhammed.muhammedsalmannewage.presentation.common.fragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ResultFragment"

@AndroidEntryPoint
class ResultFragment : ViewBindingFragment<FragmentResultBinding>() {

    private val mainViewModel by activityViewModels<MainViewModel>()

    // an Instance to destroy onDestroy
    private lateinit var nativeAd: NativeAd

    override fun initBinding(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val transition = Slide(Gravity.END)
        enterTransition = transition
        exitTransition = transition

        loadAd()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.bmiResult?.let { Log.d(TAG, "onViewCreated: ${it.bmi.asString()}") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::nativeAd.isInitialized)
            nativeAd.destroy()
    }

    /*
        An error occurs on Emulator loading the Ad,
        Will be tested on a real device if it's working.
     */
    private fun loadAd() {
        // Requirements
        val adUnitId = BuildConfig.BannerAd_ID
        val adOptions = NativeAdOptions.Builder()
            .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
        val adRequest = AdRequest.Builder().build()


        // Preparing Ad Loader
        val adLoader = AdLoader.Builder(requireContext(), adUnitId)
            .forNativeAd {
                nativeAd = it
                displayAd(it)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.e(TAG, "onAdFailedToLoad: ${p0.message}")
                }
            })
            .withNativeAdOptions(adOptions.build())
            .build()

        // Loading Ad
        adLoader.loadAd(adRequest)

    }

    private fun displayAd(ad: NativeAd) {
        with(binding) {
            val adView = adsBanner

            // displaying ad details
            // each detail has a corresponding view
            adHeadline.text = ad.headline
            adView.headlineView = adHeadline

            adBody.text = ad.body
            adView.bodyView = adBody

            adView.mediaView = adImg

            // Registering the Ad with the AdView
            adView.setNativeAd(ad)
            ad
        }


    }
}