package com.example.foodapplicationandroidproject.userdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapplicationandroidproject.userdatabase.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY username ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE (email = :username OR telephone = :username OR username = :username) AND password = :password")
    fun getUser(username: String, password: String): User

    @Query("SELECT count(*) FROM user_table WHERE email = :email AND username = :username AND telephone = :phone")
    fun checkUser(email: String, username: String, phone: String): Int

    @Query("SELECT count(*) FROM user_table WHERE (email = :username OR telephone = :username OR username = :username) AND password = :password")
    fun signinUser(username: String, password: String): Int
}