package com.eddystudio.scrollpicker

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollPickerLayoutManager(context: Context, viewOrientation: Int) : LinearLayoutManager(context) {
  init {
    // 0 = horizontal  1 = vertical
    orientation = if(viewOrientation == 0) {
      LinearLayoutManager.HORIZONTAL
    } else {
      LinearLayoutManager.VERTICAL
    }
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
    return if(orientation == LinearLayoutManager.HORIZONTAL) {
      val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
      scaleDownView()
      scrolled
    } else {
      0
    }
  }

  override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
    return if(orientation == LinearLayoutManager.VERTICAL) {
      val scrolled = super.scrollVerticallyBy(dy, recycler, state)
      scaleDownView()
      scrolled
    } else {
      0
    }
  }

  private fun scaleDownView() {
    if(orientation == HORIZONTAL) {
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
    } else {
      val mid = height / 2.0f

      var maxScale = 0f
      var midPos = 0
      for(i in 0 until childCount) {
        // Calculating the distance of the child from the center
        val child = getChildAt(i)
        val childMid = (getDecoratedTop(child!!) + getDecoratedBottom(child)) / 2.0f
        val distanceFromCenter = Math.abs(mid - childMid)

        // The scaling formula
        val scale = 1 - Math.sqrt((distanceFromCenter / height).toDouble()).toFloat() * 0.8f

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
}