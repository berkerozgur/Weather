package com.example.weather.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.weather.R

@Composable
fun HomeScreen(
    onCityClicked: (city: String) -> Unit,
    modifier: Modifier = Modifier
) {
    CitiesList(
        onCityClicked = onCityClicked,
        modifier = modifier,
    )
}

@Composable
private fun CitiesList(
    onCityClicked: (city: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cities = LocalContext.current.resources.getStringArray(R.array.turkiye_cities)
    LazyColumn(
        modifier = modifier
    ) {
        items(cities) { name ->
            TextButton(
                onClick = {
                    onCityClicked(name)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = name)
            }
        }
    }
}