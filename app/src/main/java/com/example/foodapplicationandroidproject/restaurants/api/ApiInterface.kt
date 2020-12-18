package com.example.foodapplicationandroidproject.restaurants.api

import com.example.foodapplicationandroidproject.restaurants.model.Cities
import com.example.foodapplicationandroidproject.restaurants.model.Countries
import com.example.foodapplicationandroidproject.restaurants.model.RestaurantsFromCities
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("restaurants")
    suspend fun getRestaurantsByCities(@Query("city") city: String,@Query("page") page: Int): Response<RestaurantsFromCities>

    @GET("cities")
    suspend fun getCityNames() : Response<Cities>

    @GET("restaurants")
    suspend fun getRestaurantsByCountries(@Query("country") country: String,@Query("page") page: Int): Response<RestaurantsFromCities>

    @GET("countries")
    suspend fun getCountryNames() : Response<Countries>

}