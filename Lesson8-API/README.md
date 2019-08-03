# How to connect to the Internet?
In this lesson, we will use Retrofit lib to connect the app to the internet and display their data into the RecyclerView

# Retrofit
* Add to app
```
// Retrofit to work with api
implementation "com.squareup.retrofit2:retrofit: $version_retrofit"
implementation "com.squareup.retrofit2:converter-moshi:$version_retrofit"

// Moshi for Json converter
implementation "com.squareup.moshi:moshi:$version_moshi"
implementation "com.squareup.moshi:moshi-kotlin:$version_moshi"
```
* Build a client to use to call API
  * Create file **MyApiService.kt**, create a new Retrofit client
```kotlin
private val retrofit = Retrofit.Builder()
     .addConverterFactory(MoshiConverterFactory.create(moshi)) // Add converter to convert the data into object
     .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Add the call adapter will be call after have result from API
     .build()
```
  * Create a interface API Service
```kotlin
interface MyApiService {
  @GET("user")
  fun getUsers(): Deferred<List<User>>
}
```
  * Call the API useing coroutine
``` kotlin
launch {
  val users = myApi.getUsers().await() // Using await, function will become to suspend function and it will be suspend until have the result
}
```
# RecyclerView to display data
Similar with [Lesson7](https://github.com/dinhlamvn/kotlin_android/tree/master/Lesson7-RecyclerView)
