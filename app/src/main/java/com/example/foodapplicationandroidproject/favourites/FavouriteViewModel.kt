package com.example.foodapplicationandroidproject.favourites

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.username
import com.example.foodapplicationandroidproject.userdatabase.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application): AndroidViewModel(application)  {

    private val repository : FavouriteRepository

    private var allFavorites: List<Favourites> = ArrayList()

    private val favouriteRestaurant: MutableLiveData<Favourites> by lazy {
        MutableLiveData<Favourites>()
    }

    private var favoritesByUser: List<Favourites> = mutableListOf()

    init {
        val favouritesDao = UserDatabase.getDatabase(application).favoritesDao()
        repository = FavouriteRepository(favouritesDao)
        favouriteRestaurant.value = Favourites(0,"something","random")
    }

    fun addToFavorites(favoriteRestaurant: Favourites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(favoriteRestaurant)
        }
    }


    fun getAllFavorites(): List<Favourites> {
        viewModelScope.launch(Dispatchers.IO) {
            allFavorites = repository.getAllFavorites()
        }
        return allFavorites
    }

    fun getFavoritesByUser(username: String) : List<Favourites> {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesByUser = repository.getFavoritesByUser(username)
        }
        return favoritesByUser
    }

    fun deleteFavorite(restaurantName: String, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(restaurantName, username)
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorites()
        }
    }
}