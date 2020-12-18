package com.example.foodapplicationandroidproject.favourites

import androidx.room.*

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(favoriteRestaurant: Favourites)

    @Query("select * from favourites_table where restaurantName like :restaurantName and username = :username")
    suspend fun getFavorite(restaurantName: String, username: String): Favourites

    @Query("select * from favourites_table")
    suspend fun getAllFavorites(): List<Favourites>

    @Query("select * from favourites_table where username = :username")
    suspend fun getFavoritesByUser(username: String): List<Favourites>

    @Query("delete from favourites_table where restaurantName like :restaurantName and username = :username")
    suspend fun deleteFromFavorites(restaurantName: String, username: String)

    @Query("delete from favourites_table")
    suspend fun deleteAllFavorites()
}