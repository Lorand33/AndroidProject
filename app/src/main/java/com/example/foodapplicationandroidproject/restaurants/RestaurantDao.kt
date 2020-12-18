package com.example.foodapplicationandroidproject.restaurants

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapplicationandroidproject.restaurants.model.Restaurant

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRestaurant(restaurant: Restaurant)

    @Query("Select * From restaurant_table Order by id ASC")
    fun readAllData():LiveData<List<Restaurant>>

}