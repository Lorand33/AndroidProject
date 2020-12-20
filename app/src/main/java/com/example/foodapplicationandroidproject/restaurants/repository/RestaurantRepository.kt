package com.example.foodapplicationandroidproject.restaurants.repository


import com.example.foodapplicationandroidproject.restaurants.RestaurantDao
import com.example.foodapplicationandroidproject.restaurants.model.Restaurant

/**
 * repository for restaurant's database
 * every function use a query function written at the RestaurantDao
 * every function written here will be used by the RestaurantViewModelForDatabase
 * NOT USED
 */
class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    //get all data from database
    val readAllData = restaurantDao.readAllData()

    //add a new restaurant to the database
    suspend fun addRestaurant(restaurant: Restaurant) {
        restaurantDao.addRestaurant(restaurant)
    }
}