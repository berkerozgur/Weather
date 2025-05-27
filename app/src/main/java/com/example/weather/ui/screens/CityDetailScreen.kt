package com.example.weather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.R

// Data model for weather cards
data class WeatherCardData(
    val iconId: Int,
    val title: String,
    val content: String
)

// TODO: Replace this with dynamic API response data
private val sampleWeatherCards = listOf(
    WeatherCardData(R.drawable.air_24px, "Wind", "3 km/h"),
    WeatherCardData(R.drawable.humidity_percentage_24px, "Humidity", "80%"),
    WeatherCardData(R.drawable.visibility_24px, "Visibility", "11.27 km"),
    WeatherCardData(R.drawable.compress_24px, "Pressure", "1013.5 mb")
)

@Composable
fun CityDetailScreen(
    cityName: String = "Zocca, IT", // TODO: Replace with API data
    temperature: String = "14°",    // TODO: Replace with API data
    condition: String = "Clear",    // TODO: Replace with API data
    tempRange: String = "22° / 10° Feels like 15", // TODO: Replace with API data
    cards: List<WeatherCardData> = sampleWeatherCards,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = temperature,
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                text = condition,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = tempRange,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize.times(1.1f)
                )
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cards) { card ->
                CustomCard(
                    iconId = card.iconId,
                    title = card.title,
                    content = card.content
                )
            }
        }
    }
}

@Composable
private fun CustomCard(
    iconId: Int,
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = title)
            }
            Text(
                text = content,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CityDetailScreenPreview() {
    CityDetailScreen()
}
