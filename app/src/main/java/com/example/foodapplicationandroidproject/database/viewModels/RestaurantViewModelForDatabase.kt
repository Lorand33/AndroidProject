package com.example.foodapplicationandroidproject.database.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.database.RestaurantDatabase
import com.example.foodapplicationandroidproject.database.model.Restaurant
import com.example.foodapplicationandroidproject.database.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantViewModelForDatabase(application: Application): AndroidViewModel(application)  {
    private val readAllData : LiveData<List<Restaurant>>
    private val repository : RestaurantRepository

    init{
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository= RestaurantRepository(restaurantDao)
        readAllData = repository.readAllData
    }

    fun addRestaurant(restaurant:Restaurant){
        viewModelScope.launch (Dispatchers.IO){
            repository.addRestaurant(restaurant)
        }
    }
}