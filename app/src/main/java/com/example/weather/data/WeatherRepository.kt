package com.example.weather.data

import com.example.weather.model.City
import com.example.weather.model.WeatherResponse
import com.example.weather.network.WeatherApiService

interface WeatherRepository {
    suspend fun getCities(city: String, apiKey: String): List<City>
    suspend fun getWeatherResponse(
        lat: Double,
        lon: Double,
        apiKey: String
    ): WeatherResponse
}

class NetworkWeatherRepository(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getCities(
        city: String,
        apiKey: String
    ): List<City> = weatherApiService.getCities(city, apiKey)

    override suspend fun getWeatherResponse(
        lat: Double,
        lon: Double,
        apiKey: String
    ): WeatherResponse = weatherApiService.getWeatherResponse(lat = lat, lon = lon, apiKey = apiKey)
}