package com.example.weather.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.North
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.weather.R
import com.example.weather.model.WeatherUiModel
import com.example.weather.ui.WeatherUiState
import com.example.weather.ui.theme.WeatherTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WeatherCardData(
    val iconId: Int,
    val title: String,
    val content: String,
    val rotation: Float = 0f,
    val showDirectionArrow: Boolean = false
)

@Composable
fun CityDetailScreen(
    uiState: WeatherUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        if (uiState is WeatherUiState.Success) {
            val iconUrl = "https://openweathermap.org/img/wn/${uiState.data.icon}@2x.png"
            val request = ImageRequest.Builder(context)
                .data(iconUrl)
                .build()
            context.imageLoader.enqueue(request)
        }
    }

    when (uiState) {
        is WeatherUiState.Loading -> LoadingScreen(modifier = modifier)
        is WeatherUiState.Success -> WeatherScreen(
            weather = uiState.data,
            lastUpdated = uiState.lastUpdated,
            modifier = modifier
        )

        is WeatherUiState.Error -> ErrorScreen(
            onRetry = onRetry,
            modifier = modifier
        )
    }

}

@Composable
fun WeatherScreen(
    weather: WeatherUiModel,
    lastUpdated: Long,
    modifier: Modifier = Modifier
) {
    val formattedTime =
        SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
            .format(Date(lastUpdated))
    val cityName = weather.cityName
    val temperature = weather.temperature
    val condition = weather.condition
    val tempRange = weather.feelsLike

    val cards = listOf(
        WeatherCardData(
            R.drawable.air_24px,
            "Wind",
            weather.windSpeed,
            rotation = (weather.windDeg ?: 0).toFloat(),
            showDirectionArrow = true
        ),
        WeatherCardData(
            R.drawable.humidity_percentage_24px,
            "Humidity",
            weather.humidity
        ),
        WeatherCardData(
            R.drawable.visibility_24px,
            "Visibility",
            weather.visibility
        ),
        WeatherCardData(
            R.drawable.compress_24px,
            "Pressure",
            weather.pressure
        )
    )

    Column(modifier = modifier.padding(12.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = cityName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row {
                Text(
                    text = temperature,
                    style = MaterialTheme.typography.displayLarge,
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://openweathermap.org/img/wn/${weather.icon}@2x.png")
                        .crossfade(false)
                        .build(),
                    contentDescription = weather.condition,
                    placeholder = painterResource(id = R.drawable.image_24px),
                    error = painterResource(id = R.drawable.broken_image_24px),
                    modifier = Modifier.size(80.dp)
                )
            }
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
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cards) { card ->
                CustomCard(
                    iconId = card.iconId,
                    title = card.title,
                    content = card.content,
                    rotation = card.rotation,
                    showDirectionArrow = card.showDirectionArrow,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Last updated: $formattedTime",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error_24px),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = "Failed to load",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun CustomCard(
    iconId: Int,
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    rotation: Float = 0f,
    showDirectionArrow: Boolean = false,
) {
    Card(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                if (showDirectionArrow) {
                    Icon(
                        imageVector = Icons.Filled.North,
                        contentDescription = "Wind direction",
                        modifier = Modifier
                            .rotate((rotation + 180f) % 360f)
                            .size(20.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CityDetailScreenPreview() {
    WeatherTheme {
        CityDetailScreen(
            uiState = WeatherUiState.Success(mockWeatherUiModel, System.currentTimeMillis()),
            onRetry = {}
        )
    }
}

private val mockWeatherUiModel = WeatherUiModel(
    cityName = "İstanbul",
    temperature = "24°",
    condition = "Clear",
    feelsLike = "Feels like 23°",
    pressure = "1012 mb",
    humidity = "60%",
    visibility = "10.0 km",
    windSpeed = "3.5 km/h",
    windDeg = 333,
    icon = "01d"
)
