package com.example.foodapplicationandroidproject.userdatabase.repository

import androidx.lifecycle.LiveData
import com.example.foodapplicationandroidproject.userdatabase.UserDao
import com.example.foodapplicationandroidproject.userdatabase.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user : User){
        userDao.addUser(user)
    }

    fun getUser(username: String, password: String): User {
        return userDao.getUser(username, password)
    }

    fun findUser(email: String, username: String, phone: String): Int {
        return userDao.checkUser(email, username, phone)
    }

    fun loginUser(username: String, password: String): Int {
        return userDao.signinUser(username, password)
    }

    fun countUsers() : Int {
        return userDao.countUsers()
    }

}