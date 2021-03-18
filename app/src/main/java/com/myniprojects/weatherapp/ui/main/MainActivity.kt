package com.myniprojects.weatherapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import com.myniprojects.weatherapp.ui.screens.MainScreen
import com.myniprojects.weatherapp.ui.theme.WeatherAppTheme
import com.myniprojects.weatherapp.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // test, getting simple response
        lifecycleScope.launchWhenCreated {
            viewModel.getCurrentWeather("New York").collectLatest {
                Timber.d("Collected $it")
            }
        }

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
