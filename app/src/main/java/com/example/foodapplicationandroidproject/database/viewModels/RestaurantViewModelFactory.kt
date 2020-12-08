package com.example.foodapplicationandroidproject.database.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplicationandroidproject.database.repository.Repository

class RestaurantViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repository) as T
    }
}