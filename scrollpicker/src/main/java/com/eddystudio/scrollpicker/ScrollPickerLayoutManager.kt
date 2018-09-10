package com.eddystudio.scrollpicker

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollPickerLayoutManager(context: Context) : LinearLayoutManager(context) {
  init {
    orientation = HORIZONTAL
  }

  var onItemSelectedListener: OnItemSelectedListener? = null
  var onItemUnselectedListener: OnItemUnselectedListener? = null
  private lateinit var recyclerView: RecyclerView

  override fun onAttachedToWindow(view: RecyclerView?) {
    super.onAttachedToWindow(view)
    recyclerView = view!!
  }

  override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
    super.onLayoutChildren(recycler, state)
    scaleDownView()
  }

  override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
    if(orientation == LinearLayoutManager.HORIZONTAL) {
      val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
      scaleDownView()
      return scrolled
    } else {
      return 0
    }
  }

  private fun scaleDownView() {
    val mid = width / 2.0f

    var maxScale = 0f
    var midPos = 0
    for(i in 0 until childCount) {
      // Calculating the distance of the child from the center
      val child = getChildAt(i)
      val childMid = (getDecoratedLeft(child!!) + getDecoratedRight(child)) / 2.0f
      val distanceFromCenter = Math.abs(mid - childMid)

      // The scaling formula
      val scale = 1 - Math.sqrt((distanceFromCenter / width).toDouble()).toFloat() * 0.8f

      // Set scale to view
      child.scaleX = scale
      child.scaleY = scale

      if(onItemUnselectedListener != null) {
        onItemUnselectedListener?.unselected(child)
      }

      if(scale > maxScale) {
        maxScale = scale
        midPos = i
      }
    }

    val current = recyclerView.getChildLayoutPosition(getChildAt(midPos)!!)
    if(onItemSelectedListener != null) {
      onItemSelectedListener?.onSelected(getChildAt(midPos)!!, current)
    }
  }
}