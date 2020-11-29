package com.example.foodapplicationandroidproject.database.model

import androidx.room.Entity

@Entity(tableName = "city_table")
data class Cities (
    val count : Int,
    val cities : List<String>
)