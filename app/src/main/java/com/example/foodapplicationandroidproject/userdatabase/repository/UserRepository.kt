package com.example.foodapplicationandroidproject.userdatabase.repository

import androidx.lifecycle.LiveData
import com.example.foodapplicationandroidproject.userdatabase.UserDao
import com.example.foodapplicationandroidproject.userdatabase.model.User

/**
 * every function use a query function written at the UserDao
 * every function written here will be used by the UserViewModel
 * some functions are using coroutines("suspend")
 * table = user_table
 */
class UserRepository(private val userDao: UserDao) {
    //get every user from the table
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    //add a new user to the table
    suspend fun addUser(user : User){
        userDao.addUser(user)
    }

    //get the user with specific parameters from the table
    fun getUser(username: String, password: String): User {
        return userDao.getUser(username, password)
    }

    //get the number of users with specific parameters - used at registration
    fun findUser(email: String, username: String, phone: String): Int {
        return userDao.checkUser(email, username, phone)
    }

    //get the number of users with specific parameters - used at login
    fun loginUser(username: String, password: String): Int {
        return userDao.signinUser(username, password)
    }

    //get the number of users
    fun countUsers() : Int {
        return userDao.countUsers()
    }

}