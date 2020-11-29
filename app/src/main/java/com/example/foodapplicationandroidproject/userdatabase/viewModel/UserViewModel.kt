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


class UserViewModel(application: Application):AndroidViewModel(application) {
    private val repository: UserRepository
    //TODO: lateinit error
    lateinit var login: LiveData<Int>
    lateinit var register: LiveData<Int>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun findUser(email : String, username : String, telephone : String) {
        viewModelScope.launch(Dispatchers.IO) {
             register = repository.findUser(email,username,telephone)
        }
    }

    fun signInUser(username : String, password : String){
        viewModelScope.launch(Dispatchers.IO) {
            login = repository.loginUser(username,password)
        }
    }
}