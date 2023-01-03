package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.result

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.transition.Slide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.muhammed.muhammedsalmannewage.BuildConfig
import com.muhammed.muhammedsalmannewage.R
import com.muhammed.muhammedsalmannewage.databinding.FragmentResultBinding
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMI
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIResult
import com.muhammed.muhammedsalmannewage.domain.model.bmi.WeightClass
import com.muhammed.muhammedsalmannewage.domain.model.bmi.name
import com.muhammed.muhammedsalmannewage.presentation.activity.bmi.MainViewModel
import com.muhammed.muhammedsalmannewage.presentation.core.common.fragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "ResultFragment"

@AndroidEntryPoint
class ResultFragment : ViewBindingFragment<FragmentResultBinding>() {

    private val viewModel by viewModels<ResultViewModel>()
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
        doOnStateChange()

        mainViewModel.bmiResult?.let { bmiResult ->
            viewModel.onBMIResult(
                name = mainViewModel.name,
                bmiResult = bmiResult
            )
        }

        // Action Buttons clicks
        with(binding) {
            bmiRateBtn.setOnClickListener {
                viewModel.rateApp(
                    packageName = requireContext().packageName
                )
            }

            bmiShareBtn.setOnClickListener {
                viewModel.takeScreenshot(
                    view = binding.bmiCard,
                    saveLocation = requireActivity().cacheDir.absolutePath
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::nativeAd.isInitialized)
            nativeAd.destroy()
    }

    private fun doOnStateChange() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.onEach { state ->
                state.shareIntent?.let { startActivity(Intent.createChooser(it, "Share Image")) }
                state.error?.let { showError(it) }
                state.rateIntent?.let { rIntent ->
                    try {
                        startActivity(rIntent)
                    } catch (e: ActivityNotFoundException) {
                        startActivity(state.rateIntentSecondary)
                    }
                }

                state.bmiResult?.let { bmiResult ->
                    displayBMIResult(
                        name = state.name,
                        bmiResult = bmiResult
                    )
                }

                displayAd(state.showAds)


            }.launchIn(this)
        }
    }

    /*
        An error occurs on Emulator loading the Ad,
        Works fine on real devices.
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
                viewModel.onAdLoaded()
            }

            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    viewModel.onAdFailed()
                }
            })
            .withNativeAdOptions(adOptions.build())
            .build()

        // Loading Ad
        adLoader.loadAd(adRequest)

    }

    // Note: AdChoicesView will appear when the adUnitId isn't the testing one.
    private fun displayAd(show: Boolean) {
        if (show) {
            binding.adsBanner.visibility = View.VISIBLE
            with(binding) {
                val ad = nativeAd
                val adView = adsBanner
                // Setup adViews with layout views
                adView.headlineView = adHeadline
                adView.bodyView = adBody
                adView.mediaView = adImg
                adView.callToActionView = adEventButton
                adView.adChoicesView = adChoices

                // displaying ad details
                // These assets are guaranteed to be available
                adHeadline.text = ad.headline
                adBody.text = ad.body
                adView.mediaView?.mediaContent = ad.mediaContent
                adView.mediaView?.setImageScaleType(ImageView.ScaleType.CENTER_CROP)

                // These assets aren't guaranteed to be available
                adView.callToActionView?.visibility =
                    if (ad.callToAction == null) View.INVISIBLE else View.VISIBLE

                // Registering the Ad with the AdView
                adView.setNativeAd(ad)
                ad
            }
        } else
            binding.adsBanner.visibility = View.GONE


    }

    private fun displayBMIResult(name: String, bmiResult: BMIResult) {
        setBMIResultText(bmiResult.bmi)
        val weightClass = bmiResult.weightClass

        // Displaying name with WeightClass
        val nameWithClass = getString(R.string.bmi_name_with_type, name, weightClass.name())
        binding.bmiNameWithClassTv.text = nameWithClass

        // Displaying BMI range
        val bmiRange = if (weightClass != WeightClass.OBESE)
            getString(R.string.bmi_range, weightClass.name(), weightClass.start, weightClass.end)
        else
            getString(R.string.bmi_range_obese, weightClass.start)

        binding.bmiRange.text = bmiRange

        // Displaying Ponderal Index
        val pi = getString(R.string.ponderal_index, bmiResult.pi.pi)
        binding.ponderalIndex.text = pi
    }

    private fun setBMIResultText(bmi: BMI) {
        val spannedText = SpannableString(bmi.trimmed())
        spannedText.setSpan(
            RelativeSizeSpan(2f),
            0,
            bmi.integerLength,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.bmiPercentage.text = spannedText
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }
}