package com.example.foodapplicationandroidproject.restaurants.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.restaurants.RestaurantDatabase
import com.example.foodapplicationandroidproject.restaurants.model.Restaurant
import com.example.foodapplicationandroidproject.restaurants.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * NOT USED
 */
class RestaurantViewModelForDatabase(application: Application): AndroidViewModel(application)  {
    //variable for the restaurants
    private val readAllData : LiveData<List<Restaurant>>
    //variable for the repository
    private val repository : RestaurantRepository

    init{
        //defining the dao, getting the abstract function from the RestaurantDatabase(where the restaurant_table is included)
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        //initializing the repository
        repository= RestaurantRepository(restaurantDao)
        //add value using function from the repository
        readAllData = repository.readAllData
    }

    //add new restaurant to the database
    fun addRestaurant(restaurant:Restaurant){
        viewModelScope.launch (Dispatchers.IO){
            repository.addRestaurant(restaurant)
        }
    }
}