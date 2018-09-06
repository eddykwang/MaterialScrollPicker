package com.eddystudio.horizontalscrollpicker

import com.eddystudio.quickrecyclerviewadapterlib.QuickRecyclerViewBaseAdapter

class MyAdapter(val list: ArrayList<ViewModel>) : QuickRecyclerViewBaseAdapter(BR.viewmodel) {
  override fun getLayoutId(): Int {
    return R.layout.item_layout
  }

  override fun getLayoutIdForPosition(p0: Int): Int {
    return R.layout.item_layout
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun getObjectForPosition(p0: Int): Any {
    return list[p0]
  }

}