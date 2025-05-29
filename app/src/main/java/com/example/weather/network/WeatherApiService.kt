package com.example.weather.network

import com.example.weather.model.City
import com.example.weather.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("geo/1.0/direct")
    suspend fun getCities(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): List<City>

    @GET("data/2.5/weather")
    suspend fun getWeatherResponse(
        @Query("units") units: String = "metric",
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse
}