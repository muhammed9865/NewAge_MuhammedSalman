package com.muhammed.muhammedsalmannewage.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.muhammed.muhammedsalmannewage.databinding.ListItemMetricBinding
import com.muhammed.muhammedsalmannewage.presentation.viewholder.MetricViewHolder

typealias ItemSelectedListener<T> = (T) -> Unit
class CountableMetricAdapter <T: Any> : ListAdapter<T, MetricViewHolder<T>>(CountableDiffUtil()) {

    private var onItemSelectedListener: ItemSelectedListener<T>? = null
    private var selectedItemPosition = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetricViewHolder<T> {
        val binding =
            ListItemMetricBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MetricViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MetricViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(
            item = item,
            isSelected = selectedItemPosition == position,
        )

        val centerViewItem = getItem(selectedItemPosition)
        onItemSelectedListener?.invoke(centerViewItem)
    }

    fun setOnItemSelectedListener(listener: ItemSelectedListener<T>) {
        this.onItemSelectedListener = listener
    }


    // create the highlightItem method to highlight the item in the center
    fun highlightItem(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }

    fun getPositionAndHighlight(item: T) : Int {
        val itemPosition = currentList.indexOf(item)
        return itemPosition
    }

    private val TAG = "CountableMetricAdapter"

}

class CountableDiffUtil <T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}
