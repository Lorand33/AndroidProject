package com.example.foodapplicationandroidproject.database.model

data class RestaurantsFromCities (
    val total_entries : Int,
    val per_page : Int,
    var current_page : Int,
    val restaurants : List<Restaurant>
)
