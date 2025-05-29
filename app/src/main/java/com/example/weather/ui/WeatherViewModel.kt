package com.example.weather.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weather.WeatherApplication
import com.example.weather.data.WeatherRepository
import com.example.weather.model.WeatherUiModel
import com.example.weather.model.toUiModel
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.launch
import java.io.IOException

val dotenv = dotenv {
    directory = "/assets"
    filename = "env"
}

sealed interface WeatherUiState {
    data class Success(val data: WeatherUiModel) : WeatherUiState
    object Error : WeatherUiState
    object Loading : WeatherUiState
}

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set
    var selectedCity: String by mutableStateOf("")
        private set

    fun getWeatherForCity(city: String, apiKey: String = dotenv["API_KEY"]) {
        viewModelScope.launch {
            weatherUiState = WeatherUiState.Loading
            selectedCity = city
            try {
                val cities = weatherRepository.getCities(city, apiKey)
                val selected = cities.first()
                val weather = weatherRepository.getWeatherResponse(
                    lat = selected.lat,
                    lon = selected.lon,
                    apiKey = apiKey
                )
                weatherUiState = WeatherUiState.Success(weather.toUiModel())
            } catch (e: IOException) {
                weatherUiState = WeatherUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WeatherApplication)
                val fooRepository = application.container.weatherRepository
                WeatherViewModel(weatherRepository = fooRepository)
            }
        }
    }
}