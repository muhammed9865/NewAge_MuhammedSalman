package com.muhammed.muhammedsalmannewage.presentation.activity.bmi.fragment

import androidx.fragment.app.Fragment
import com.muhammed.muhammedsalmannewage.databinding.FragmentResultBinding
import com.muhammed.muhammedsalmannewage.presentation.common.fragment.ViewBindingFragment

class ResultFragment : ViewBindingFragment<FragmentResultBinding>() {

    override fun initBinding(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }
}