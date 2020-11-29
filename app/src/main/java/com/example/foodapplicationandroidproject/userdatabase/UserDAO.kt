package com.example.foodapplicationandroidproject.userdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapplicationandroidproject.userdatabase.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT count(*) FROM user_table WHERE email = :email AND username = :username AND telephone = :phoneNumber")
    fun findUser(email: String, username: String, phoneNumber: String): LiveData<Int>

    @Query("SELECT count(*) FROM user_table WHERE (email = :username OR telephone = :username OR username = :username) AND password = :password")
    fun loginUser(username: String, password: String): LiveData<Int>
}