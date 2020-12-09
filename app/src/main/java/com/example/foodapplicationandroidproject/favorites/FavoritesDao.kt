package com.example.foodapplicationandroidproject.favorites

import androidx.room.*
import com.example.foodapplicationandroidproject.favorites.Favorites

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorites(favorite: Favorites)

    @Query("Select * From favorites_table")
    fun getAllFavorites(): List<Favorites>

    @Query("Select * From favorites_table Where username = :username")
    suspend fun getFavoritesByUser(username: String): List<Favorites>

    @Delete
    suspend fun deleteFromFavorites(favorite: Favorites)

}