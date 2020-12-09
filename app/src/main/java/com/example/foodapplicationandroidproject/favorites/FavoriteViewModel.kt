package com.example.foodapplicationandroidproject.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.userdatabase.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FavoriteRepository
    private var readAllFavorites : List<Favorites>
    private val favoriteRestaurant : MutableLiveData<Favorites>
    private val favoritesByUser : MutableLiveData<List<Favorites>>

    init {
        val favoritesDao = UserDatabase.getDatabase(application).favoritesDao()
        repository = FavoriteRepository(favoritesDao)
        readAllFavorites = repository.getAllFavorites()
        favoriteRestaurant = MutableLiveData<Favorites>()
        favoritesByUser = MutableLiveData<List<Favorites>>()
        favoriteRestaurant.value = Favorites(0,"something","other")
    }

    fun getAllFavoritesByUser() = readAllFavorites

    fun getFavoriteRestaurant() = favoriteRestaurant

    fun getFavoritesByUser() = favoritesByUser

    fun addToFavorites(favorite : Favorites){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(favorite)
        }
    }


    fun getAllFavorites(): List<Favorites> {
        viewModelScope.launch(Dispatchers.IO) {
            readAllFavorites = repository.getAllFavorites()
        }
        return readAllFavorites
    }

    fun getFavoritesByUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val restaurantList: List<Favorites>? = repository.getFavoritesByUser(username)
            favoritesByUser.postValue(restaurantList)
        }
    }

    fun deleteFromFavorites(restaurant: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromFavorites(restaurant)
        }
    }
}