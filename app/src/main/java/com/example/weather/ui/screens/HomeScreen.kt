package com.example.weather.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.weather.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
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
        CitiesList(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun CitiesList(
    modifier: Modifier = Modifier
) {
    val cities = LocalContext.current.resources.getStringArray(R.array.turkiye_cities)
    LazyColumn(
        modifier = modifier
    ) {
        items(cities) { name ->
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = name)
            }
        }
    }
}