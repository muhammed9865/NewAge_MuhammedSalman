package com.muhammed.muhammedsalmannewage.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.muhammed.muhammedsalmannewage.databinding.ListItemMetricBinding
import com.muhammed.muhammedsalmannewage.presentation.viewholder.MetricViewHolder

class CountableMetricAdapter : ListAdapter<Int, MetricViewHolder<Int>>(CountableDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetricViewHolder<Int> {
        val binding =
            ListItemMetricBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MetricViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MetricViewHolder<Int>, position: Int) {
        holder.bind(getItem(position))
    }

}

class CountableDiffUtil : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }
}
