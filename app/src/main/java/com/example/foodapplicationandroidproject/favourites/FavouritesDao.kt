package com.example.foodapplicationandroidproject.favourites

import androidx.room.*

@Dao
/**
 * defining commands which can happen in the favourites_table
 * the commands will be used using coroutines("suspend" word)
 * they are going to be used at the FavouriteRepository
 */
interface FavouritesDao {
    //add a restaurant to the favourites_table
    //if it is already included, the command is ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(favoriteRestaurant: Favourites)

    //return a restaurant from the favourites_table filtered by name and user's username
    @Query("select * from favourites_table where restaurantName like :restaurantName and username = :username")
    suspend fun getFavorite(restaurantName: String, username: String): Favourites

    //return a list with the favourite restaurants(all)
    @Query("select * from favourites_table")
    suspend fun getAllFavorites(): List<Favourites>

    //return a list with the favourite restaurants(user's favourite)
    @Query("select * from favourites_table where username = :username")
    suspend fun getFavoritesByUser(username: String): List<Favourites>

    //delete one restaurant from the favourite_table
    //the searching is based on restaurant name and user's username
    @Query("delete from favourites_table where restaurantName like :restaurantName and username = :username")
    suspend fun deleteFromFavorites(restaurantName: String, username: String)

    //delete everything from the favourites_table
    @Query("delete from favourites_table")
    suspend fun deleteAllFavorites()
}