package com.example.foodapplicationandroidproject.database.model


data class RestaurantsFromCities (
    val total_entries : Int,
    val per_page: Int,
    val current_page : Int,
    val restaurants : List<Restaurant>
)
