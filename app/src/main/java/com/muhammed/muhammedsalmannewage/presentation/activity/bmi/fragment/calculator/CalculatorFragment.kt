package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment.calculator

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.muhammed.muhammedsalmannewage.BuildConfig
import com.muhammed.muhammedsalmannewage.R
import com.muhammed.muhammedsalmannewage.databinding.FragmentCalculatorBinding
import com.muhammed.muhammedsalmannewage.databinding.MetricSelectorBinding
import com.muhammed.muhammedsalmannewage.domain.model.bmi.BMIResult
import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender
import com.muhammed.muhammedsalmannewage.presentation.activity.bmi.MainViewModel
import com.muhammed.muhammedsalmannewage.presentation.adapter.CountableMetricAdapter
import com.muhammed.muhammedsalmannewage.presentation.core.common.fragment.ViewBindingFragment
import com.muhammed.muhammedsalmannewage.presentation.core.common.view.CenterLinearLayoutManager
import com.muhammed.muhammedsalmannewage.presentation.core.common.view.hideKeyboard
import com.muhammed.muhammedsalmannewage.presentation.core.common.view.onDone
import com.muhammed.muhammedsalmannewage.presentation.core.common.view.setImeAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "CalculatorFragment"

@AndroidEntryPoint
class CalculatorFragment : ViewBindingFragment<FragmentCalculatorBinding>() {

    private val viewModel by viewModels<CalculatorViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    private lateinit var interstitialAd: InterstitialAd

    // RVs Adapters
    private val weightAdapter by lazy { CountableMetricAdapter<Int>() }
    private val heightAdapter by lazy { CountableMetricAdapter<Int>() }
    private val genderAdapter by lazy { CountableMetricAdapter<Gender>() }

    override fun initBinding(): FragmentCalculatorBinding {
        return FragmentCalculatorBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        loadAd()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
        observeStateChange()

        // Scroll to items and highlight on config change
        val state = viewModel.state.value
        Log.d(TAG, "onViewCreated: called")
        scrollToItem(binding.heightSelector, heightAdapter, state.selectedHeight)
        scrollToItem(binding.weightSelector, weightAdapter, state.selectedWeight)
        scrollToItem(binding.genderSelector, genderAdapter, state.selectedGender)

        // To get the name on any configuration change
        binding.nameInputField.setText(mainViewModel.name)

        val transition = Slide(Gravity.START)
        enterTransition = transition
        exitTransition = transition

        binding.nameInputField.setImeAction(EditorInfo.IME_ACTION_DONE)

        // Listeners
        with(binding) {
            calculateBtn.setOnClickListener {
                viewModel.calculate()
            }

            nameInputField.doOnTextChanged { text, _, _, _ ->
                mainViewModel.name = text.toString()
            }

            nameInputField.onDone {
                it.hideKeyboard()
                it.clearFocus()
            }
        }

    }


    private fun observeStateChange() {
        viewModel.state.onEach { state ->
            with(state) {

                weightAdapter.submitList(weightsList)
                heightAdapter.submitList(heightsList)
                genderAdapter.submitList(genderList)

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
            // To display the Ad in a full mode screen
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


    private fun <T : Any> scrollToItem(binding: MetricSelectorBinding, adapter: CountableMetricAdapter<T>, item: T) {
        val itemPosition = adapter.getPositionAndHighlight(item)
        binding.metricRv.smoothScrollToPosition(itemPosition)
    }

    private fun onBMIResultAvailable(bmiResult: BMIResult) {
        mainViewModel.bmiResult = bmiResult
        viewModel.afterBMIResultsPublished()
        findNavController().navigate(R.id.resultFragment)
    }

    private fun setUpRecyclerViews() {
        initRecyclerViewWithBinding(
            binding.weightSelector,
            weightAdapter,
        ) { weight ->
            viewModel.setWeight(weight)
        }
        initRecyclerViewWithBinding(
            binding.heightSelector, heightAdapter,
        ) { height ->
            viewModel.setHeight(height)
        }
        initRecyclerViewWithBinding(
            binding.genderSelector, genderAdapter,
        ) { gender ->
            viewModel.setGender(gender)
        }

    }

    private fun <T : Any> initRecyclerViewWithBinding(
        binding: MetricSelectorBinding,
        cAdapter: CountableMetricAdapter<T>,
        onItem: (T) -> Unit,
    ) {
        // Setting up recycler view with adapter and adding a SnapEffect for scrolling
        binding.metricRv.apply {
            adapter = cAdapter
            layoutManager =
                CenterLinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            clipToPadding = false
        }

        // SnapHelper adds a special effect to the RecyclerView
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.metricRv)
        binding.metricRv.onFlingListener = null


        // Highlighting the center item in the recycler view
        highlightCenterItem(
            binding.metricRv,
            cAdapter,
            snapHelper
        )

        cAdapter.setOnItemSelectedListener(onItem)
    }

    private fun highlightCenterItem(
        recyclerView: RecyclerView,
        adapter: CountableMetricAdapter<*>,
        snapHelper: PagerSnapHelper,
    ) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        // Adding the listener just to highlight the center item
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val centerView = snapHelper.findSnapView(layoutManager)
                val position = layoutManager.getPosition(centerView!!)
                adapter.highlightItem(position)

                val firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                // If the visible items are two items, that means the last item is centered
                // So I had to highlight it manually since  the recyclerview doesn't scroll to the last item
                if (lastVisiblePosition - firstVisiblePosition == 1) {
                    adapter.highlightItem(lastVisiblePosition)
                }
            }

        })
    }

}