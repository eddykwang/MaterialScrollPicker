package com.eddystudio.scrollpicker

import android.view.View
import androidx.databinding.ObservableField

data class PickerViewModel(val string: String) {
  val tv = ObservableField<String>(string)
  lateinit var onclickListenner: ClickEvent

  public fun setOnClickListener(cl: ClickEvent) {
    this.onclickListenner = cl
  }

  public fun onClick(view: View) {
    onclickListenner.onClicked(view, string)
  }

  public interface ClickEvent {
    public fun onClicked(view: View, string: String)
  }
}