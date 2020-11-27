package com.example.foodapplicationandroidproject.userdatabase.repository

import com.example.foodapplicationandroidproject.userdatabase.UserDao
import com.example.foodapplicationandroidproject.userdatabase.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun addUser(user : User){
        userDao.addUser(user)
    }

    suspend fun findUser(email: String, username : String, telephone : String):Int{
        return userDao.findUser(email,username,telephone)
    }
}