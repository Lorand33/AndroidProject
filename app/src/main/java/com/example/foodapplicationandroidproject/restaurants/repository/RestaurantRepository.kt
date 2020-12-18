package com.example.foodapplicationandroidproject.restaurants.repository


import com.example.foodapplicationandroidproject.restaurants.RestaurantDao
import com.example.foodapplicationandroidproject.restaurants.model.Restaurant

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    val readAllData = restaurantDao.readAllData()

    suspend fun addRestaurant(restaurant: Restaurant) {
        restaurantDao.addRestaurant(restaurant)
    }
}