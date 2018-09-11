package com.eddystudio.materialscrollpicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
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
        .scrollViewAdapter(scrollPickerAdapter)
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

//**************************************************************************************************\

    val l2 = ArrayList<ViewModel>()
    l2.add(ViewModel("Monday"))
    l2.add(ViewModel("Tuesday"))
    l2.add(ViewModel("Wednesday"))
    l2.add(ViewModel("Thursday"))
    l2.add(ViewModel("Friday"))
    l2.add(ViewModel("Saturday"))
    l2.add(ViewModel("Sunday"))

    val sp2 = findViewById<ScrollPickerView<ViewModel>>(R.id.pk2)
    val scrollPickerAdapter2 = ScrollPickerAdapter(l2, R.layout.item_layout, BR.viewmodel)
    ScrollPickerView.Builder(sp2)
        .scrollViewAdapter(scrollPickerAdapter2)
        .onItemSelectedListener(object : OnItemSelectedListener {
          override fun onSelected(view: View, layoutPosition: Int) {
            tv.text = l2[layoutPosition].obsIndex.get()
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.white))
          }

        })
        .onItemUnselectedListener(object : OnItemUnselectedListener {
          override fun unselected(view: View) {
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.holo_green_light))
          }
        })
        .build()


//**************************************************************************************************



    val l3 = ArrayList<ViewModel>()
    l3.add(ViewModel("What was the name of your elementary / primary school?"))
    l3.add(ViewModel("In what city or town does your nearest sibling live?"))
    l3.add(ViewModel("What is your pet’s name?"))
    l3.add(ViewModel("In what year was your father born?"))
    l3.add(ViewModel("What is the first name of the person you first kissed?"))
    l3.add(ViewModel("What time of the day were you born? (hh:mm)"))
    l3.add(ViewModel("What is the last name of your math teacher?"))

    val sp3 = findViewById<ScrollPickerView<ViewModel>>(R.id.pk_vertical)
    val scrollPickerAdapter3 = ScrollPickerAdapter(l3, R.layout.vertical_layout_item, BR.viewmodel)
    ScrollPickerView.Builder(sp3)
        .scrollViewAdapter(scrollPickerAdapter3)
        .onItemSelectedListener(object : OnItemSelectedListener {
          override fun onSelected(view: View, layoutPosition: Int) {
            tv.text = l3[layoutPosition].obsIndex.get()
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.white))
          }

        })
        .onItemUnselectedListener(object : OnItemUnselectedListener {
          override fun unselected(view: View) {
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.white))
          }
        })
        .build()
  }


}
