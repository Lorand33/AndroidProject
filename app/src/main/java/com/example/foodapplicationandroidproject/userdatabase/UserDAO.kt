package com.example.foodapplicationandroidproject.userdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapplicationandroidproject.userdatabase.model.User

@Dao
/**
 * defining commands which can happen in the user_table
 * the commands will be used using coroutines("suspend" word)
 * they are going to be used at the UserRepository
 */
interface UserDao {
    //add a user to the user_table
    //if it is already included, the command is ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    //return a list with the users
    @Query("SELECT * FROM user_table ORDER BY username ASC")
    fun readAllData(): LiveData<List<User>>

    //return a user found by different parameters
    @Query("SELECT * FROM user_table WHERE (email = :username OR telephone = :username OR username = :username) AND password = :password")
    fun getUser(username: String, password: String): User

    //return the number of users based on different parameters
    @Query("SELECT count(*) FROM user_table WHERE email = :email AND username = :username AND telephone = :phone")
    fun checkUser(email: String, username: String, phone: String): Int

    //return the number of users found by different parameters
    @Query("SELECT count(*) FROM user_table WHERE (email = :username OR telephone = :username OR username = :username) AND password = :password")
    fun signinUser(username: String, password: String): Int

    //count the number of users
    @Query("Select count(*) from user_table")
    fun countUsers(): Int
}