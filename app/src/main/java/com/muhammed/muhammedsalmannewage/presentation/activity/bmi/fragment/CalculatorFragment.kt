package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment

import androidx.fragment.app.Fragment
import com.muhammed.muhammedsalmannewage.databinding.FragmentCalculatorBinding
import com.muhammed.muhammedsalmannewage.presentation.common.fragment.ViewBindingFragment

class CalculatorFragment : ViewBindingFragment<FragmentCalculatorBinding>() {

    override fun initBinding(): FragmentCalculatorBinding {
        return FragmentCalculatorBinding.inflate(layoutInflater)
    }
}