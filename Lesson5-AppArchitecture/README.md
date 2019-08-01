# Application Architecture - Build app with MVVM model

This is the app build with **MVVM** model in Android. The components of it is: **Model**, **View**, **ViewModel**

# MVVM Model
* **Model**: The model of project, it have data class and logic for them.
* **View**: The view as Activity, Fragment,...It get interact from user and call view model to handle.
* **ViewModel**: The ViewModel object, include function to handle interact from **View** and work with **Model** to do logic code.

# LiveData
The component of android Jetpack, it can update real time data to **View** and has the lifecycle of **View**, that mean it can destroy when activity or fragment was destroyed.

# How to build MVVM
* Open the **build.gradle** module app, add below line:
```
// Live data and view model
implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
```
* Create the class extends from **ViewModel**
```kotlin
class GameViewModel: ViewModel() {
  // Some code here
  ...
  override fun onCleared() {
    super.onCleared()
    // Do somethings when view model was destroyed
  }
}
```
* In **GameFragment** add a object type of view model
```kotlin
// The game view model
private lateinit var viewModel: GameViewModel
```
* Using **ViewModelProviders** to initialize the **ViewModel**
```kotlin
viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
```
# How to pass the parameters to ViewModel
Using ViewModelFactory to do that, you can:
* Add the class extends from ViewModelFactory
```kotlin
class ScoreViewModelFactory(private val score: Int): ViewModelProvider.Factory {
  // Some code here
}
```
* Override the function **create**
```kotlin
override fun <T: ViewModel?> create(modelClass: Class<T>): T {
  // Check the param class is type of ViewModel
  if (modeClass.isAssignableFrom(ScoreViewModel::class.java)) {
    // If this correct, return ViewModel object with params
    return ScoreViewModel(score) as T
  }
  ...
}
```
# Why is require the ViewModelFactory?
* ViewModel is init by ViewModelProvider, can not pass the parameter to from constructor
* ViewModel assign with the view lifecycle, if using instantiate when config change it will create a new instance of ViewModel instead of get the ViewModel was created by using ViewModelFactory
