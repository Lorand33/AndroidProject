package com.example.foodapplicationandroidproject.favorites


class FavoriteRepository(private val favoriteDao: FavoritesDao) {
    suspend fun addToFavorites(favorite: Favorites) {
        favoriteDao.addFavorites(favorite)
    }

    fun getAllFavorites(): List<Favorites> {
        return favoriteDao.getAllFavorites()
    }

    suspend fun getFavoritesByUser(username: String): List<Favorites> {
        return favoriteDao.getFavoritesByUser(username)
    }

    suspend fun deleteFromFavorites(favorite: Favorites) {
        favoriteDao.deleteFromFavorites(favorite)
    }

}