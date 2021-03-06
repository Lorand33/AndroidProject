package com.example.foodapplicationandroidproject.restaurants.api

import com.example.foodapplicationandroidproject.restaurants.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * generating an implementation of the ApiInterface interface
 */
object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiInterface by lazy{
        retrofit.create(ApiInterface::class.java)
    }
}