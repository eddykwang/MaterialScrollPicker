package com.eddystudio.scrollpicker

import android.view.View

interface OnItemSelectedListener{
  fun onSelected(view: View, layoutPosition: Int)
}