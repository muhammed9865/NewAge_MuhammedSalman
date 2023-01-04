package com.muhammed.muhammedsalmannewage.presentation.core.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Used this LayoutManager instead of adding manual padding in the layout file @metric_selector.xml
// To make the top and bottom paddings are dynamic and depends on the height of the item
open class CenterLinearLayoutManager : LinearLayoutManager {
    constructor(context: Context) : super(context)
    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context,
        orientation,
        reverseLayout)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes)

    private lateinit var recyclerView: RecyclerView

    override fun measureChildWithMargins(child: View, widthUsed: Int, heightUsed: Int) {
        val lp = (child.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        super.measureChildWithMargins(child, widthUsed, heightUsed)
        if (lp != 0 && lp != itemCount - 1) return

        // This is the padding part, depending on the recycler view orientation
        // In VERTICAL, Add a padding of half of the item height to the top and bottom of the recycler view
        // In HORIZONTAL, Add a padding of half of the item width to the start and end of the recycler view
        val padding = if (orientation == VERTICAL) {
            (height - child.measuredHeight) / 2
        } else {
            (width - child.measuredWidth) / 2
        }.coerceAtLeast(0
        )
        when (orientation) {
            RecyclerView.HORIZONTAL -> {
                if (!reverseLayout) {
                    recyclerView.updatePaddingRelative(start = padding, end = padding)
                } else {
                    recyclerView.updatePaddingRelative(start = padding, end = padding)
                }
            }
            RecyclerView.VERTICAL -> {
                if (!reverseLayout) {
                    recyclerView.updatePaddingRelative(top = padding, bottom = padding)
                } else {
                    recyclerView.updatePaddingRelative(bottom = padding, top = padding)
                }
            }
        }
    }

    // capture host recyclerview
    override fun onAttachedToWindow(view: RecyclerView) {
        recyclerView = view
        super.onAttachedToWindow(view)
    }
}