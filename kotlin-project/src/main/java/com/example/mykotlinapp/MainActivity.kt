package com.example.mykotlinapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Entity class for User
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val isPrivate: Boolean,  // Changed from String to Boolean
    val login: String,
    val avatarUrl: String,
    val type: String,
    val description: String? = "No description"  // Made nullable with a default value
)


// DAO Interface
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
}



// Database class
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

// Retrofit API interface
interface ApiService {
    @GET("users")
    suspend fun fetchUsers(): List<User>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.github.com/repositories"

    val instance: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}

// MainActivity class
class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val fetchButton = findViewById<Button>(R.id.button)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        // Adjust window insets for UI padding to accommodate system bars (like status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize database using Room
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "user_database")
            .fallbackToDestructiveMigration(false) // Avoid data loss with version changes
            .build()

        // Fetch and display data from API
        fetchButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    // Fetch data from the API
                    val users = RetrofitClient.instance.fetchUsers()

                    // Insert valid users into Room database
                    users.forEach { user ->
                        val validUser = user.copy(description = user.description ?: "No description")
                        database.userDao().insertUser(validUser)
                    }

                    // Retrieve stored users and display them in the TextView
                    val storedUsers = database.userDao().getAllUsers()  // This works fine with the proper DAO
                    val displayText = storedUsers.joinToString("\n") { "${it.login} - ${it.fullName}" }
                    textView.text = displayText

                    // Show success message
                    Toast.makeText(this@MainActivity, "Data fetched & stored!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    // Show error message if API call fails
                    "Error: ${e.message}".also { textView.text = it }
                }
            }
        }

        // Delete data from the database
        deleteButton.setOnClickListener {
            lifecycleScope.launch {
                // Delete all users from the Room database
                database.userDao().deleteAllUsers()
                // Show message indicating all data has been deleted
                "All data deleted".also { textView.text = it }
                Toast.makeText(this@MainActivity, "Data deleted successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
