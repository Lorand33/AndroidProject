package com.example.foodapplicationandroidproject.restaurants

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapplicationandroidproject.restaurants.model.Restaurant

@Dao
/**
 * defining commands which can happen in the restaurant_table
 * they are going to be used at the RestaurantRepository
 */
interface RestaurantDao {
    //add a restaurant to the restaurant_table
    //if it is already included, the command is ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRestaurant(restaurant: Restaurant)

    //get all restaurants from restaurant_table
    @Query("Select * From restaurant_table Order by id ASC")
    fun readAllData():LiveData<List<Restaurant>>

}