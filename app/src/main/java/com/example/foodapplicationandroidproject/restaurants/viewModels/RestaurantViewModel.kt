package com.example.foodapplicationandroidproject.restaurants.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapplicationandroidproject.restaurants.model.Cities
import com.example.foodapplicationandroidproject.restaurants.model.Countries
import com.example.foodapplicationandroidproject.restaurants.model.RestaurantsFromCities
import com.example.foodapplicationandroidproject.restaurants.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * functions are using repository's functions
 * functions written here can be called where the RestaurantViewModel will be defined
 * coroutines are used
 */
class RestaurantViewModel(private val repository: Repository) : ViewModel() {
    //variable for response - restaurants from countries
    val responseRestaurantsFromCountries : MutableLiveData<Response<RestaurantsFromCities>> = MutableLiveData()
    //variable for response - countries
    val responseCountries : MutableLiveData<Response<Countries>> = MutableLiveData()
    //variable for response - restaurants from cities
    val responseRestaurantsFromCities: MutableLiveData<Response<RestaurantsFromCities>> = MutableLiveData()
    //variable for response - cities
    val responseCities : MutableLiveData<Response<Cities>> = MutableLiveData()

    //get restaurants by countries
    fun getRestaurantsByCountry(country:String,current_page:Int){
        viewModelScope.launch {
            val response = repository.getRestaurantsByCountry(country,current_page)
            responseRestaurantsFromCountries.value = response
        }
    }

    //get countries
    fun getCountries(){
        viewModelScope.launch {
            val response = repository.getCountries()
            responseCountries.value=response
        }
    }

    //get restaurants by citites
    fun getRestaurantsByCity(city :String,page:Int){
        viewModelScope.launch{
            val response = repository.getRestaurantsByCity(city,page)
            responseRestaurantsFromCities.value= response
        }
    }

    //get cities
    fun getCities(){
        viewModelScope.launch {
            val response = repository.getCities()
            responseCities.value = response
        }
    }

}