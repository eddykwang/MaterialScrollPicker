package com.eddystudio.horizontalscrollpicker

import android.content.Context
import android.os.Bundle
import android.view.View


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearSnapHelper

import androidx.recyclerview.widget.RecyclerView
import android.util.DisplayMetrics


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    setupScrollView()
  }

  private fun setupScrollView() {
    val selectedTv = findViewById<AppCompatTextView>(R.id.selected_item_tv)
    val recyclerView = findViewById<RecyclerView>(R.id.horizontal_recyclerview)
    val list = ArrayList<ViewModel>()
    for(i in 1..30) {
      val vm = ViewModel(i.toString())
      val lisenner: ViewModel.ClickEvent = object : ViewModel.ClickEvent {
        override fun onClicked(view: View, string: String) {
          val pos = recyclerView.getChildAdapterPosition(view)
          recyclerView.smoothScrollToPosition(pos)
        }
      }
      vm.setOnClickListener(lisenner)
      list.add(vm)
    }
    val adapter = MyAdapter(list)

    val padding = windowManager.defaultDisplay.width / 2 - convertDpToPixel(32f, this).toInt()
    recyclerView.setPadding(padding, 0, padding, 0)
    recyclerView.adapter = adapter
    val layoutManager = MyLinearLayoutManager(this)
    layoutManager.callback = object : MyLinearLayoutManager.OnItemSelectedListener {
      override fun onItemSelected(layoutPosition: Int) {
        selectedTv.text = list[layoutPosition].index
      }

    }
    recyclerView.layoutManager = layoutManager
    val snapHelper = LinearSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
  }

  private fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
  }

}
