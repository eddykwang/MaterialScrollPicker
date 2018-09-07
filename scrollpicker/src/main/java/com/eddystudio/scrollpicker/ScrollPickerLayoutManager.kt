package com.eddystudio.scrollpicker

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollPickerLayoutManager(context: Context) : LinearLayoutManager(context) {
  init {
    orientation = HORIZONTAL
  }

  var onItemListener: OnItemListener? = null
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

      onItemListener!!.onItemUnselected(child)

      if(scale > maxScale) {
        maxScale = scale
        midPos = i
      }
    }


    val current = recyclerView.getChildLayoutPosition(getChildAt(midPos)!!)
    onItemListener?.onItemSelected(getChildAt(midPos)!!, current)
  }

  private fun getRecyclerViewCenterX(): Int {
    return (recyclerView.right - recyclerView.left) / 2 + recyclerView.left
  }

  interface OnItemListener {
    fun onItemSelected(view: View, layoutPosition: Int)
    fun onItemUnselected(view: View)
  }
}