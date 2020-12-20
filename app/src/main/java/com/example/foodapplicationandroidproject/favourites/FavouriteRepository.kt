package com.example.foodapplicationandroidproject.favourites

/**
 * every function use a query function written at the FavouritesDao
 * every function written here will be used by the FavouriteViewModel
 * functions are using coroutines("suspend")
 * table = favourites_table
 */
class FavouriteRepository(private val favoritesDao: FavouritesDao) {

    //add a new favourite restaurant to the table
    suspend fun addToFavorites(restaurant: Favourites) {
        favoritesDao.addToFavorites(restaurant)
    }

    //get every favourite restaurant from the table
    suspend fun getAllFavorites(): List<Favourites> {
        return favoritesDao.getAllFavorites()
    }

    //get user's favourite restaurants from the table
    suspend fun getFavoritesByUser(username: String): List<Favourites> {
        return favoritesDao.getFavoritesByUser(username)
    }

    //delete a restaurant from the favourites
    suspend fun deleteFavorite(restaurantName: String, username: String) {
        favoritesDao.deleteFromFavorites(restaurantName, username)
    }

    //delete everything from the table
    suspend fun deleteAllFavorites() {
        favoritesDao.deleteAllFavorites()
    }
}