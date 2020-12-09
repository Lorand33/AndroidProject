package com.example.foodapplicationandroidproject.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")

data class Favorites(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val restaurantName: String,
    val username: String
)