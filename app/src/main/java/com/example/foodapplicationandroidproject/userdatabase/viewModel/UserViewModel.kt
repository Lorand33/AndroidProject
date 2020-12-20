package com.example.foodapplicationandroidproject.userdatabase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.userdatabase.UserDatabase
import com.example.foodapplicationandroidproject.userdatabase.model.User
import com.example.foodapplicationandroidproject.userdatabase.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * functions are using repository's functions
 * functions written here can be called where the UserViewModel will be defined
 */
class UserViewModel(application: Application):AndroidViewModel(application) {
    //variable for the repository
    private val repository: UserRepository
    //variable for the users
    private val readAllData: LiveData<List<User>>

    init {
        //defining the dao, getting the abstract function from the UserDatabase(where the user_table is included)
        val userDao = UserDatabase.getDatabase(application).userDao()
        //initializing the repository
        repository = UserRepository(userDao)
        //add value using function from the repository
        readAllData = repository.readAllData
    }

    //add a new user to the database
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    //get a specific user from the database
    fun getUser(username: String, password: String): User {
        return repository.getUser(username, password)
    }

    //return the number of specific users - used at registration
    fun findUser(email: String, username: String, phone: String): Int {
        return repository.findUser(email, username, phone)
    }

    //return the number of specific users - used at login
    fun loginUser(username: String, password: String): Int {
        return repository.loginUser(username, password)
    }

    //return the number of users
    fun countUsers(): Int {
        return repository.countUsers()
    }
}