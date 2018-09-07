package com.eddystudio.scrollpicker

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.eddystudio.quickrecyclerviewadapterlib.QuickRecyclerViewAdapter

class ScrollPickerView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int? = 0) :
    RelativeLayout(context, attributeSet, defStyleAttr!!) {
  private val recyclerView by lazy { findViewById<RecyclerView>(R.id.picker_recyclerview) }
  private val centerView by lazy { findViewById<View>(R.id.center_view) }

  init {
    LayoutInflater.from(context).inflate(R.layout.layout_scroll_picker_view, this, true)

    val list = ArrayList<PickerViewModel>()
    for(i in 0..20) {
      val vm = PickerViewModel("$i")
      vm.onclickListenner = object : PickerViewModel.ClickEvent {
        override fun onClicked(view: View, string: String) {
          recyclerView.smoothScrollToPosition(recyclerView.getChildAdapterPosition(view))
        }
      }
      list.add(vm)
    }

    val quickRecyclerViewAdapter: QuickRecyclerViewAdapter<PickerViewModel> =
        QuickRecyclerViewAdapter(list, R.layout.layout_picker_item_view, BR.viewmodel)
    recyclerView.adapter = quickRecyclerViewAdapter

    val layoutManager = ScrollPickerLayoutManager(context)


    layoutManager.onItemListener = object : ScrollPickerLayoutManager.OnItemListener {
      override fun onItemSelected(view: View, layoutPosition: Int) {
        // action for selected item
      }

      override fun onItemUnselected(view: View) {
        //action for unselected item(s)
      }

    }
    recyclerView.layoutManager = layoutManager

    LinearSnapHelper().attachToRecyclerView(recyclerView)
  }


  private fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    val padding = w / 2 - convertDpToPixel(32f, context).toInt()
    recyclerView.setPadding(padding, 0, padding, 0)
  }
}