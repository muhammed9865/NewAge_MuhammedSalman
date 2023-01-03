package com.muhammed.muhammedsalmannewage.presentation.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.muhammed.muhammedsalmannewage.R
import com.muhammed.muhammedsalmannewage.databinding.ListItemMetricBinding
import com.muhammed.muhammedsalmannewage.domain.model.bmi.Gender

// This ViewHolder will be generic for metrics types that displayed in CalculatorFragment
class MetricViewHolder<T : Any>(
    private val binding: ListItemMetricBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T, isSelected: Boolean) {
        if (item is Gender)
            handleGenderMetric(item, itemView.context)
        else
            binding.metricValue.text = item.toString()

        handleItemStyle(isSelected)
    }

    private fun handleGenderMetric(item: T, context: Context) {
        val genderAsString = when (item as Gender) {
            Gender.MALE -> {
                context.getString(R.string.male)
            }
            Gender.FEMALE -> {
                context.getString(R.string.female)
            }
            else -> {
                context.getString(R.string.male)
            }
        }

        binding.metricValue.text = genderAsString

    }

    private fun handleItemStyle(isSelected: Boolean) {
        val styleId = if (isSelected) R.style.Metric_Selected else R.style.Metric_UnSelected
        binding.metricValue.setTextAppearance(styleId)
    }

}