# Using Nav Controller to navigate screens, what is about intent?

This lesson we have learn to use nav controller to control navigate of screens.

### Nav Controller
To use Nav Controller, in android **build.gradle** file add:
```
    implementation "android.arch.navigation:navigation-fragment-ktx:$version_navigation"
    implementation "android.arch.navigation:navigation-ui-ktx:$version_navigation"
```
In the layout, add fragment component with name **androidx.navigation.fragment.NavHostFragment**:
```xml
  <fragment
            android:id="@+id/navHost"
            android:name="androidx.navigation.fragment.NavHostFragment"
            app:navGrap="@navigation/navigation"
            app:defaultNavHost="true"
            ...
```
Add **navigation.xml** file to project by: Right click **app**, select **New -> Android Resource...**, then select ***Resource type*** is Navigation (make sure use Android Studio 3.2 or higher)

Setup it on Activity:
```kotlin
  // Find the element
  val navController = this.findNavController(R.id.navHost)
  
  // Setup the nav controller
  NaviationUI.setupNavController(this, navController)
  
  // Override the onSupportNavigateUp function to implement back event
  override fun onSupportNavigateUp(): Boolean {
    val navController = this.findNavController(R.id.navHost)
    return NavigationUI.navigateUp(navController, appbarConfiguration)
  
```

How to navigate from screen 1 to screen 2?
```
  // Handle when click some button
  button.findNavController().navigate(Screen1Directions.actionScreen1ToScreen2())
```

### Intent
Intent is an object provide method to move other activity, service, broadcase receiver...
Type of intent: **Explicit** and **Implicit**
- Explicit: Provide class to move, the system can detect correct which component to access.
```kotlin
    // Make the main activity intent
    val intent = Intent(this, AcvitityShoping::class.java)
```
- Implicit: Provide an action, data type, the system can not detect which component to access.
```kotlin
   // Make the view action intent
   val intent = Intent(Intent.ACTION_VIEW)
```

You can see more detail about Intent at: https://developer.android.com/reference/android/content/Intent
