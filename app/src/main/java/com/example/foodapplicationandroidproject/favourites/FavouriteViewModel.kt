package com.example.foodapplicationandroidproject.favourites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.userdatabase.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * functions are using repository's functions
 * functions written here can be called where the FavouriteViewModel will be defined
 */
class FavouriteViewModel(application: Application): AndroidViewModel(application)  {
    //variable for the repository
    private val repository : FavouriteRepository

    //variable for the favourite restaurants
    private var allFavorites: List<Favourites> = ArrayList()

    //variable for a favourite restaurant
    //variable's initialization will be done only when it will be called for the first time
    private val favouriteRestaurant: MutableLiveData<Favourites> by lazy {
        MutableLiveData<Favourites>()
    }

    //variable for user's favourite restaurants
    private var favoritesByUser: List<Favourites> = mutableListOf()

    init {
        //defining the dao, getting the abstract function from the UserDatabase(where the favourite_table is included)
        val favouritesDao = UserDatabase.getDatabase(application).favoritesDao()
        //initializing the repository
        repository = FavouriteRepository(favouritesDao)
        //giving start value for a newly defined restaurants
        favouriteRestaurant.value = Favourites(0,"something","random")
    }

    //add a new favourite restaurant to the table
    fun addToFavorites(restaurant: Favourites) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(restaurant)
        }
    }

    //get every favourite restaurant from the table
    fun getAllFavorites(): List<Favourites> {
        viewModelScope.launch(Dispatchers.IO) {
            allFavorites = repository.getAllFavorites()
        }
        return allFavorites
    }

    //get user's favourite restaurants from the table
    fun getFavoritesByUser(username: String) : List<Favourites> {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesByUser = repository.getFavoritesByUser(username)
        }
        return favoritesByUser
    }

    //delete a restaurant from the table
    fun deleteFavorite(restaurantName: String, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(restaurantName, username)
        }
    }

    //delete everything from the table
    fun deleteAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorites()
        }
    }
}