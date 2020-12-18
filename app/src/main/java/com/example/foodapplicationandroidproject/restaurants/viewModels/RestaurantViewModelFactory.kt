package com.example.foodapplicationandroidproject.restaurants.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplicationandroidproject.restaurants.repository.Repository

class RestaurantViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repository) as T
    }
}