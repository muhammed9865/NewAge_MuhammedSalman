package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment

import com.muhammed.muhammedsalmannewage.databinding.FragmentResultBinding
import com.muhammed.muhammedsalmannewage.presentation.common.fragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : ViewBindingFragment<FragmentResultBinding>() {

    override fun initBinding(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }
}