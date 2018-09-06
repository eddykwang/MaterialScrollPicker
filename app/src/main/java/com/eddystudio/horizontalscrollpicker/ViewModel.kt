package com.eddystudio.horizontalscrollpicker

import android.view.View
import androidx.databinding.ObservableField

data class ViewModel(val index: String) {
  val obsIndex = ObservableField<String>()
  lateinit var onclickListenner: ClickEvent

  init {
    obsIndex.set(index)
  }

  public fun setOnClickListener(cl: ClickEvent) {
    this.onclickListenner = cl
  }

  public fun onClick(view: View) {
    onclickListenner.onClicked(view, index)
  }

  public interface ClickEvent {
    public fun onClicked(view: View, string: String)
  }
}