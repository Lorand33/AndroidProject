package com.example.foodapplicationandroidproject.favourites

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.foodapplicationandroidproject.userdatabase.model.User

@Entity(tableName = "favourites_table", foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["username"], childColumns = ["username"], onDelete = ForeignKey.CASCADE)])
data class Favourites(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val restaurantName: String,
    val username: String
)