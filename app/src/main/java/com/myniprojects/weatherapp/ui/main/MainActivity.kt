package com.myniprojects.weatherapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.myniprojects.weatherapp.ui.theme.WeatherAppTheme
import com.myniprojects.weatherapp.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}
