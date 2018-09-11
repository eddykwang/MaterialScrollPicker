# Material Scroll Picker (Android)
### A beautiful and easy to use scoll picker libary, it supports both vertical and horizontal view.
#### Highlight Features:
* Support both vertical and horizonal scroll
* You can pass a View instead of specific type of data
* Beautiful Metarial Design

#### Before use:
* You need use databind for your itemview, which means you need have a ```viewmodel``` and a ```bindId```.

## Dependencies

[![](https://jitpack.io/v/eddykwang/MaterialScrollPicker.svg)](https://jitpack.io/#eddykwang/MaterialScrollPicker)

```
dependencies {
	    implementation 'com.github.eddykwang:MaterialScrollPicker:1.0a'
}
```

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

## Screenshot
<img src="https://github.com/eddykwang/HorizontalScrollPicker/blob/master/screenshot/screenshot_gif.gif" data-canonical-src="https://github.com/eddykwang/HorizontalScrollPicker/blob/master/screenshot/screenshot_gif.gif" width="300" height="600" />

## How to use
#### layout
```xml
  <com.eddystudio.scrollpicker.ScrollPickerView
    android:id="@+id/scrollpickerview"
    android:layout_width="match_parent"
    android:layout_height="68dp"
    android:layout_margin="16dp"
    app:pickerOrientation="horizontal"
    app:pickerViewBgColor="@color/design_default_color_primary"
    app:centerPointerBgColor="@color/colorAccent"
    app:centerPointerHeight="68dp"
    app:centerPointerWidth="42dp"/>
```

#### create view
```kotlin
    val scrollPickerView = findViewById<ScrollPickerView<ViewModel>>(R.id.scrollpickerview)
    //Adapter takes the list of item, the layout for the item, and the bindId.
    val scrollPickerAdapter = ScrollPickerAdapter(list, R.layout.item_layout, BR.viewmodel)
    
    ScrollPickerView.Builder(scrollPickerView)
        .scrollViewAdapter(scrollPickerAdapter)
        .onItemSelectedListener(object : OnItemSelectedListener {
          override fun onSelected(view: View, layoutPosition: Int) {
	  //call back for selected item
            tv.text = list[layoutPosition].index
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.white))
          }

        })
        .onItemUnselectedListener(object : OnItemUnselectedListener {
          override fun unselected(view: View) {
	  //call back for all unselected item
            view.findViewById<AppCompatTextView>(R.id.scroll_view_tv)
                .setTextColor(getColor(android.R.color.darker_gray))
          }

        })
        .build()
```
