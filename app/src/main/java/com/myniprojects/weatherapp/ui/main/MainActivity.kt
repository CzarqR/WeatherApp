package com.myniprojects.weatherapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.myniprojects.weatherapp.ui.screens.MainScreen
import com.myniprojects.weatherapp.ui.theme.WeatherAppTheme
import com.myniprojects.weatherapp.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // To test faster, delete it at the end
        viewModel.cityName.component2().invoke("melbourne")
        viewModel.getCurrentWeather()

        setContent {
            WeatherAppTheme {

                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}
