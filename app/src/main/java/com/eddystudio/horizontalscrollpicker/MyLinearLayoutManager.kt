package com.eddystudio.horizontalscrollpicker

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
  init {
    orientation = HORIZONTAL
  }

  var callback: OnItemSelectedListener? = null
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

      val tv = child.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
      tv.setTextColor(recyclerView.context.getColor(android.R.color.darker_gray))


      if(scale > maxScale) {
        maxScale = scale
        midPos = i
      }
    }
    val current = recyclerView.getChildLayoutPosition(getChildAt(midPos)!!)
    callback?.onItemSelected(current)
    val textView = getChildAt(midPos)!!.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
    textView.setTextColor(recyclerView.context.getColor(android.R.color.white))
    textView.elevation = 100f
  }

  private fun getRecyclerViewCenterX(): Int {
    return (recyclerView.right - recyclerView.left) / 2 + recyclerView.left
  }

  interface OnItemSelectedListener {
    fun onItemSelected(layoutPosition: Int)
  }
}