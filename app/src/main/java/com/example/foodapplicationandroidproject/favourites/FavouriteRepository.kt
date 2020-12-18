package com.example.foodapplicationandroidproject.favourites

class FavouriteRepository(private val favoritesDao: FavouritesDao) {

    suspend fun addToFavorites(restaurant: Favourites) {
        favoritesDao.addToFavorites(restaurant)
    }

    suspend fun getFavorite(restaurantName: String, username: String): Favourites {
        return favoritesDao.getFavorite(restaurantName, username)
    }

    suspend fun getAllFavorites(): List<Favourites> {
        return favoritesDao.getAllFavorites()
    }

    suspend fun getFavoritesByUser(username: String): List<Favourites> {
        return favoritesDao.getFavoritesByUser(username)
    }

    suspend fun deleteFavorite(restaurantName: String, username: String) {
        favoritesDao.deleteFromFavorites(restaurantName, username)
    }

    suspend fun deleteAllFavorites() {
        favoritesDao.deleteAllFavorites()
    }
}