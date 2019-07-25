# Lesson 2 - Build the layout  and use data binding

This lesson help you how to build the layout and work with data biding in Android.

Android have some layout components:
- View
  - TextView: To display a text to screen
  - Button: To perform user interactive
  - ImageView: To display an image to screen
  - Spinner: Dropdown list item
  - Checkbox: The checkable view
  - RadioButton: The selectable view
  - And more...
- ViewGroup:
  - FrameLayout: The basic layout, contains single view or more views
  - LinearLayout: The layout with relative is VERTICAL or HORIZONTAL
  - ScrollView: The scrollable view, make content can scroll to vertical or horizontal
  - TableLayout: The table layout with row and column
  - And more..

How to use android data binding:

Please goto **build.gradle** module **app** and in **android {** tag, add below line:
```
  dataBinding {
    enabled = true
  }
```
Goto layout file, replace root with **layout**  tag:
```xml
  <layout .../>
    ...
    <!-- This is elements -->
    ...
  </layout>
```

After this step, please rebuild the project to make Android generate the layout binding file for you. Then, in **MainAcitivity** replace ***setContentView*** method with:
```kotlin
  binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
  ...// some your code
```

Apply the data binding to layout, add **data** and **variable** tag to your activity layout file:
```xml
  <data>
    <variable name="myData" 
              type="com.android.example.data.MyData" />
  </data>
```

And set the binding data in activity:
```kotlin
  val mydata = MyData()
  binding.myData = mydata
```

Now, you know how to make the layout in android and using the data binding.

