# Lesson 6 - Using Room Database into the application

This lesson will talk about how to use **Room** database in your application and using **Coroutines** to work with database (insert, load, update).

# Summary:
* Room Database
* DAO (Data Access Object)
* Application Architecture with Room
* Coroutines to work with database
# Room?
Room is API help you work with Database easier and clean. It use annotation to work with database. Example: @Entity to make a class is table in database, @PrimaryKey to make a field is a privary key.

* SQL Database:
  * **Entity**: Object or concept to store in the database. Entity class defines a table, each instance is stored as a table row
  * **Query**: Request for data or information from a database table or tables, or a request to perform some action on the data.

* DAO Annotations:
  * @Insert: To insert a new data to table
  * @Delete: Remove a data from table
  * @Update: Update data of table
  * @Query: Get data from table or tables
  * @Database: Make a class is a database manager

* Example:
  * Build an entity:
```kotlin
// Make this class is an entity
@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
  // Make field in primary key, auto increament
  @PrimaryKey(autoGenerate = true)
  val nightId: Long = 0L,
  
  // Make field is a column
  @ColumnInfo(name = "start_time_milli")
  val startTime: Long = System.currentTimeMillis(),
  
  ...
)
```
  * Build a DAO
```kotlin
interface DatabaseDao {
  // Create a function to insert new data
  @Insert
  fun insert(night: SleepNight)

  // Create a function to get all data from table
  @Query("select * from daily_sleep_quality_table order by nightId desc")
  fun getAllNights(): LiveData<List<SleepNight>>
  
  //....
}
```
  * Build a database manager
```kotlin
@Database(entities = [SleepNight::class.java], version = 1, exportSchema = false)
abstract class SleepDatabase: RoomDatabase() {
  // Declare DAO
  abstract val sleepDatabaseDao: SleepDatabaseDao

  // Make a singleton object
  companion object {
    // ...
    INSTANCE = Room.databaseBuilder(context.applicationContext,
      SleepDatabase::class.java, "sleep_history_database")
      .fallbackToDestructiveMigration().build()
    )
    ...
  }
}
```
# Kotlin Coroutines
* Asynchronous: Parallel processing
* Non-blocking: Not block main thead
* Sequential code
* Kotlin Coroutines:
  * **Job**: A background job conceptually, a job is a cancellable thing a life cycle that culminate in its completion
  * **Dispatcher**: The dispatcher sends of corountines to run on various threads
  * **Scope**: Combine information, including job and dispatcher, to define a context in which the corountines runs
  * **Suspend keyword**: To make a normal function to a suspend function, it will be suspend until have a return value
* Example to use coroutines to work with database
```kotlin
// Define a job
val viewModelJob = Job()

// Define scope
val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

// Using coroutines to load a night from database
fun getNight(id: Long) {
  viewModelScope.launch {
    val night = getNightFromDatabase(id)
  }
}

suspend fun getNightFromDatabase(id: Long) {
  // Using background thread to load a data from database
  withContext(Dispatchers.IO) {
    return databaseDao.loadNight(id)
  }
}
```
Coroutines to make sure the database operation don't block UI thread and make a smooth user experience for your app.
