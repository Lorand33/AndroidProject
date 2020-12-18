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

class RestaurantViewModel(private val repository: Repository) : ViewModel() {
    val responseRestaurantsFromCountries : MutableLiveData<Response<RestaurantsFromCities>> = MutableLiveData()
    val responseCountries : MutableLiveData<Response<Countries>> = MutableLiveData()
    val responseRestaurantsFromCities: MutableLiveData<Response<RestaurantsFromCities>> = MutableLiveData()
    val responseCities : MutableLiveData<Response<Cities>> = MutableLiveData()

    fun getRestaurantsByCountry(country:String,current_page:Int){
        viewModelScope.launch {
            val response = repository.getRestaurantsByCountry(country,current_page)
            responseRestaurantsFromCountries.value = response
        }
    }
    fun getCountries(){
        viewModelScope.launch {
            val response = repository.getCountries()
            responseCountries.value=response
        }
    }
    fun getRestaurantsByCity(city :String,page:Int){
        viewModelScope.launch{
            val response = repository.getRestaurantsByCity(city,page)
            responseRestaurantsFromCities.value= response
        }
    }

    fun getCities(){
        viewModelScope.launch {
            val response = repository.getCities()
            responseCities.value = response
        }
    }

}