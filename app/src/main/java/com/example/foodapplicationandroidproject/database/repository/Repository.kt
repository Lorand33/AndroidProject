package com.example.foodapplicationandroidproject.database.repository

import com.example.foodapplicationandroidproject.api.RetrofitInstance
import com.example.foodapplicationandroidproject.database.model.Cities
import com.example.foodapplicationandroidproject.database.model.Countries
import com.example.foodapplicationandroidproject.database.model.RestaurantsFromCities
import retrofit2.Response

class Repository {
    suspend fun getRestaurantsByCountry(country :String,page:Int): Response<RestaurantsFromCities> = RetrofitInstance.api.getRestaurantsByCountries(country,page)

    suspend fun getCountries(): Response<Countries> = RetrofitInstance.api.getCountryNames()

    suspend fun getRestaurantsByCity(city : String,page :Int): Response<RestaurantsFromCities> =
            RetrofitInstance.api.getRestaurantsByCities(city,page)

    suspend fun getCities() : Response<Cities> = RetrofitInstance.api.getCityNames()
}