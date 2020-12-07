package com.example.foodapplicationandroidproject.userdatabase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.userdatabase.UserDatabase
import com.example.foodapplicationandroidproject.userdatabase.model.User
import com.example.foodapplicationandroidproject.userdatabase.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application):AndroidViewModel(application) {
    private val repository: UserRepository
    private val readAllData: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun getUser(username: String, password: String): User {
        return repository.getUser(username, password)
    }

    fun findUser(email: String, username: String, phone: String): Int {
        return repository.findUser(email, username, phone)
    }

    fun loginUser(username: String, password: String): Int {
        return repository.loginUser(username, password)
    }
}