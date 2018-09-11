package com.eddystudio.materialscrollpicker

import androidx.databinding.ObservableField

data class ViewModel(val index: String) {
  val obsIndex = ObservableField<String>()

  init {
    obsIndex.set(index)
  }
}