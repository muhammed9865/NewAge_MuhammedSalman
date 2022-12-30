package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment

import com.muhammed.muhammedsalmannewage.databinding.FragmentCalculatorBinding
import com.muhammed.muhammedsalmannewage.presentation.common.fragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculatorFragment : ViewBindingFragment<FragmentCalculatorBinding>() {

    override fun initBinding(): FragmentCalculatorBinding {
        return FragmentCalculatorBinding.inflate(layoutInflater)
    }
}