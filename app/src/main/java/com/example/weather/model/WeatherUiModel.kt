package com.example.weather.model

data class WeatherUiModel(
    val cityName: String,
    val temperature: String,
    val condition: String,
    val tempRange: String,
    val pressure: String,
    val humidity: String,
    val visibility: String,
    val windSpeed: String,
    val windDeg: Int?,
    val icon: String
)

fun WeatherResponse.toUiModel(): WeatherUiModel {
    val temp = main?.temp?.toInt()?.toString() ?: "--"
    val tempMin = main?.tempMin?.toInt()?.toString() ?: "--"
    val tempMax = main?.tempMax?.toInt()?.toString() ?: "--"
    val feelsLike = main?.feelsLike?.toInt()?.toString() ?: "--"
    val weatherMain = weather.firstOrNull()

    return WeatherUiModel(
        cityName = name ?: "Unknown City",
        temperature = "$temp°",
        condition = weatherMain?.main ?: "N/A",
        tempRange = "$tempMax° / $tempMin° Feels like $feelsLike°",
        pressure = "${main?.pressure ?: 0} mb",
        humidity = "${main?.humidity ?: 0}%",
        visibility = "%.2f km".format((visibility ?: 0) / 1000.0),
        windSpeed = "${wind?.speed ?: 0.0} km/h",
        windDeg = wind?.deg,
        icon = weatherMain?.icon ?: ""
    )
}