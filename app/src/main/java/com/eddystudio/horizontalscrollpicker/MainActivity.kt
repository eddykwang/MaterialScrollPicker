package com.eddystudio.horizontalscrollpicker

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import com.eddystudio.scrollpicker.OnItemSelectedListener
import com.eddystudio.scrollpicker.OnItemUnselectedListener
import com.eddystudio.scrollpicker.ScrollPickerAdapter
import com.eddystudio.scrollpicker.ScrollPickerView


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    setupScrollView()

  }

  private fun setupScrollView() {

    val tv = findViewById<AppCompatTextView>(R.id.selected_item_tv)

    val list = ArrayList<ViewModel>()
    for(i in 1..30) {
      val vm = ViewModel(i.toString())
      list.add(vm)
    }

    val scrollPickerView = findViewById<ScrollPickerView<ViewModel>>(R.id.scrollpickerview)

    val scrollPickerAdapter = ScrollPickerAdapter(list, R.layout.item_layout, BR.viewmodel)
    ScrollPickerView.Builder(scrollPickerView)
        .qucickRecyclerViewAdapter(scrollPickerAdapter)
        .onItemSelectedListener(object : OnItemSelectedListener {
          override fun onSelected(view: View, layoutPosition: Int) {
            tv.text = list[layoutPosition].index
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.white))
          }

        })
        .onItemUnselectedListener(object : OnItemUnselectedListener {
          override fun unselected(view: View) {
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.darker_gray))
          }

        })
        .build()
  }

}
