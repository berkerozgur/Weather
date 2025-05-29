package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weather.ui.WeatherViewModel
import com.example.weather.ui.screens.CityDetailScreen
import com.example.weather.ui.screens.HomeScreen
import com.example.weather.ui.theme.WeatherTheme

enum class WeatherScreen {
    Home,
    Detail
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                WeatherApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel =
        viewModel(factory = WeatherViewModel.Factory),
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Weather")
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = WeatherScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = WeatherScreen.Home.name) {
                HomeScreen(
                    onCityClicked = { city ->
                        viewModel.getWeatherForCity(city)
                        navController.navigate(WeatherScreen.Detail.name)
                    }
                )
            }
            composable(route = WeatherScreen.Detail.name) {
                CityDetailScreen(
                    uiState = viewModel.weatherUiState,
                    onRetry = { viewModel.getWeatherForCity(viewModel.selectedCity) }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    WeatherTheme {
        WeatherApp()
    }
}