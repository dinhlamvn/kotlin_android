# RecyclerView - How to use?

This lesson says how to use the recyclerview in your app and make it to be better by using **Databinding**, **DiffUtil**, add **Header**.

# Advantage of RecyclerView
* Efficient display of large list
* Minimizing refreshs when an item is updated, deleted or added to the list
* Reusing vuews that scroll off screen to dispay the next item that scrolls on screen
* Displaying items in a list or grid
* Scrolling veritcally or horizontally
* Allowing custom layouts when a list or a grid isn't enough for this usecase

# Three important components of RecyclerView 
* Adapter
  * How to draw view
  * How many items to draw
  * Create new item
* ViewHolder
  * Hold views
  * Store information for RecyclerView
* LayoutManager: Make recycler display list or grid, vertically or horizontally

# How to use RecyclerView in Android app
* Build an Adapter
```kotlin
class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {
  // Implement three 3 method
  
  // How to create a item view
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    // Do something with layout inflater
  }
  
  // How to draw a view
  override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
    // Do some thing to bind data to view
  }
  
  // How many item to draws
  override fun getItemCount() = [HOW MANY]
}
```
* Build a ViewHolder
```kotlin
class MyViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
  // Do somethings
}
```
* Set a layout manager to RecyclerView
```kotlin
  with(recylerView) {
    // I'm using LinearLayoutManager to display list vertically
    layoutManager = LinearLayoutManager(context)
  }
```
# Using DiffUtil to notify data change
* Create class extend from DiffUtil.ItemCallback
```kotlin
class MyAdapterDiffCallback: DiffUtil.ItemCallback<MyData> {
  // Implement two method
  // Are items the same to make two value are same or not
  override fun areItemsTheSame(oldItem: MyData, newItem: MyData): Boolean {
    // Do somethings to make 2 item are same
  }
  
  // Are content same, to make 2 item are same the content of it
  override fun areContentsTheSame(oldItem: MyData, newItem: MyData): Boolean {
    // Do somethings to make 2 item have same the content
  }
}
```
* Change Adapter to extend the ListAdapter with DiffUtil is a parameter in constructor
```kotlin
class MyAdapter: ListAdapter<MyData, MyViewHolder>(MyAdapterDiffCallback()) {
  
}
```
* Remove **data list** parameter and **getItemCout**. Using **getItem** in **onBindViewHolder** to get an data item
* Using **Adapter Submit Method** to use data list in adapter
```kotlin
fun addList(list: List<MyData>) {
  submitList(list)
}
```
# Using DataBinding in ViewHolder
* Enabled data binding in app
* Change parameter of ViewHolder to binding object
```kotlin
..(...binding: MyListBinding): ...(binding.root)
```
* Using binding in bind method
```kotlin
fun bind(data: MyData) {
  binding.data = data
  binding.executePendingBindings()
}
```
For more details, you can see an example code in my project
