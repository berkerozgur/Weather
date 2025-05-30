package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weather.ui.WeatherViewModel
import com.example.weather.ui.screens.CityDetailScreen
import com.example.weather.ui.screens.HomeScreen
import com.example.weather.ui.theme.WeatherTheme

enum class WeatherScreen(val title: String) {
    Home(title = "Home"),
    Detail(title = "Detail")
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
fun WeatherAppBar(
    currentScreen: WeatherScreen,
    canNavigateBack: Boolean,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = currentScreen.title)
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun WeatherApp(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel =
        viewModel(factory = WeatherViewModel.Factory),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = WeatherScreen.valueOf(
        value = backStackEntry?.destination?.route ?: WeatherScreen.Home.name
    )
    Scaffold(
        topBar = {
            WeatherAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateBack = { navController.navigateUp() }
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