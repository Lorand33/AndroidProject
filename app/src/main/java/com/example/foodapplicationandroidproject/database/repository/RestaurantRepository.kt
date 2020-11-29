package com.example.foodapplicationandroidproject.database.repository


import com.example.foodapplicationandroidproject.database.RestaurantDao
import com.example.foodapplicationandroidproject.database.model.Restaurant

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    val readAllData = restaurantDao.readAllData()

    suspend fun addRestaurant(restaurant: Restaurant) {
        restaurantDao.addRestaurant(restaurant)
    }
}