package com.example.foodapplicationandroidproject.restaurants.repository

import com.example.foodapplicationandroidproject.restaurants.api.RetrofitInstance
import com.example.foodapplicationandroidproject.restaurants.model.Cities
import com.example.foodapplicationandroidproject.restaurants.model.Countries
import com.example.foodapplicationandroidproject.restaurants.model.RestaurantsFromCities
import retrofit2.Response

/**
 * every function returns a call's result from the api
 */
class Repository {
    //get restaurants by countries
    //parameters: country name, page number
    suspend fun getRestaurantsByCountry(country :String,page:Int): Response<RestaurantsFromCities> = RetrofitInstance.api.getRestaurantsByCountries(country,page)

    //get countries
    suspend fun getCountries(): Response<Countries> = RetrofitInstance.api.getCountryNames()

    //get restaurants by cities
    //parameters: city name, page number
    suspend fun getRestaurantsByCity(city : String,page :Int): Response<RestaurantsFromCities> =
            RetrofitInstance.api.getRestaurantsByCities(city,page)

    //get cities
    suspend fun getCities() : Response<Cities> = RetrofitInstance.api.getCityNames()
}