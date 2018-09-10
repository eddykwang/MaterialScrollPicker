package com.eddystudio.scrollpicker

import android.view.View
import com.eddystudio.quickrecyclerviewadapterlib.QuickRecyclerViewBaseAdapter

class ScrollPickerAdapter<T>(val list: List<T>, val layout_id: Int, bindId: Int) : QuickRecyclerViewBaseAdapter(bindId) {
  var onItemClickListener: OnItemClickListener? = null

  override fun onBindViewHolder(holder: QuickRecyclerViewHolder, position: Int, payloads: MutableList<Any>) {
    super.onBindViewHolder(holder, position, payloads)
    val item = holder.itemView
    item.setOnClickListener { onItemClickListener!!.onClicked(item, position) }
  }

  override fun getLayoutId(): Int {
    return layout_id
  }

  override fun getLayoutIdForPosition(p0: Int): Int {
    return layout_id
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun getObjectForPosition(p0: Int): T {
    return list[p0]
  }

  interface OnItemClickListener {
    fun onClicked(view: View, position: Int)
  }
}