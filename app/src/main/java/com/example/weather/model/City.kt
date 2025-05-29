package com.example.weather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    val name: String,
    @SerialName("local_names")
    val localNames: Map<String, String>,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)
