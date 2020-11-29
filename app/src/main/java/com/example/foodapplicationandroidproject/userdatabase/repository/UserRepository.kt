package com.example.foodapplicationandroidproject.userdatabase.repository

import androidx.lifecycle.LiveData
import com.example.foodapplicationandroidproject.userdatabase.UserDao
import com.example.foodapplicationandroidproject.userdatabase.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun addUser(user : User){
        userDao.addUser(user)
    }

    suspend fun findUser(email: String, username : String, telephone : String): LiveData<Int>{
        return userDao.findUser(email,username,telephone)
    }

    suspend fun loginUser(username: String, password: String): LiveData<Int> {
        return userDao.loginUser(username, password)
    }
}