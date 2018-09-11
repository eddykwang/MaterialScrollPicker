package com.eddystudio.scrollpicker

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class ScrollPickerView<T> @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int? = 0) :
    RelativeLayout(context, attributeSet, defStyleAttr!!) {
  private val recyclerView by lazy { findViewById<RecyclerView>(R.id.picker_recyclerview) }
  private val centerView by lazy { findViewById<View>(R.id.center_view) }
  private val typedArray by lazy { getContext().obtainStyledAttributes(attributeSet, R.styleable.ScrollPickerView) }

  private var scrollPickerAdapter: ScrollPickerAdapter<T>? = null
  private var onItemSelectedListener: OnItemSelectedListener? = null
  private var onItemUnselectedListener: OnItemUnselectedListener? = null

  // 0 = horizontal  1 = vertical
  private var orientation: Int = 0
  private val HORIZONTAL = 0
  private val VERTICAL = 1

  init {
    val view = LayoutInflater.from(context).inflate(R.layout.layout_scroll_picker_view, this, true)

    try {
      view.setBackgroundColor(typedArray.getColor(R.styleable.ScrollPickerView_pickerViewBgColor, context.getColor(R.color.scrollViewDefaultBgColor)))
      centerView.setBackgroundColor(typedArray.getColor(R.styleable.ScrollPickerView_centerPointerBgColor, context.getColor(R.color.scrollviewPointerBgColor)))
      val centerParams = RelativeLayout.LayoutParams(typedArray.getDimension(R.styleable.ScrollPickerView_centerPointerWidth, 0f).toInt(),
          typedArray.getDimension(R.styleable.ScrollPickerView_centerPointerHeight, 0f).toInt())
      centerParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
      centerView.layoutParams = centerParams
      orientation = typedArray.getInteger(R.styleable.ScrollPickerView_pickerOrientation, 0)
    } finally {
      typedArray.recycle()
    }
  }

  private fun setup() {
    scrollPickerAdapter!!.onItemClickListener = object : ScrollPickerAdapter.OnItemClickListener {
      override fun onClicked(view: View, position: Int) {
        recyclerView.smoothScrollToPosition(position)
      }
    }

    recyclerView.adapter = scrollPickerAdapter
    val layoutManager = ScrollPickerLayoutManager(context, orientation)

    layoutManager.onItemSelectedListener = onItemSelectedListener
    layoutManager.onItemUnselectedListener = onItemUnselectedListener

    recyclerView.layoutManager = layoutManager
    LinearSnapHelper().attachToRecyclerView(recyclerView)
    recyclerView.smoothScrollToPosition(0)
  }

  private fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
//    val padding = w / 2 - convertDpToPixel(32f, context).toInt()
    if(orientation == HORIZONTAL) {
      val padding = w / 2 - if(recyclerView.getChildAt(0) != null) recyclerView.getChildAt(0).width / 2 else 0
      recyclerView.setPadding(padding, 0, padding, 0)
    } else {
      val padding = h / 2 - if(recyclerView.getChildAt(0) != null) recyclerView.getChildAt(0).height / 2 else 0
      recyclerView.setPadding(0, padding, 0, padding)
    }
  }

  class Builder<T>(private val scrollPickerView: ScrollPickerView<T>) {
    fun build() {
      if(scrollPickerView.scrollPickerAdapter != null) {
        scrollPickerView.setup()
      } else {
        throw Exception("scrollViewAdapter is required!")
      }
    }

    fun scrollViewAdapter(adapter: ScrollPickerAdapter<T>): Builder<T> {
      scrollPickerView.scrollPickerAdapter = adapter
      return this
    }

    fun onItemSelectedListener(onItemSelectedListener: OnItemSelectedListener): Builder<T> {
      scrollPickerView.onItemSelectedListener = onItemSelectedListener
      return this
    }

    fun onItemUnselectedListener(onItemUnselectedListener: OnItemUnselectedListener): Builder<T> {
      scrollPickerView.onItemUnselectedListener = onItemUnselectedListener
      return this
    }

  }
}