package com.myniprojects.weatherapp.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.myniprojects.weatherapp.ui.screens.MainScreen
import com.myniprojects.weatherapp.ui.theme.WeatherAppTheme
import com.myniprojects.weatherapp.utils.Constants
import com.myniprojects.weatherapp.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        loadLastCity()

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

    private fun loadLastCity()
    {
        /**
         * Would be better if it was in [MainViewModel] but I got:
         *
         * 'java.lang.IllegalStateException: Reading a state that
         * was created after the snapshot was taken or in a
         * snapshot that has not yet been applied'
         *
         * when trying to load and search in init block in VM
         */
        sharedPreferences.getString(Constants.SH_LAST_CITY_KEY, null)?.let {
            Timber.d("Last city from SH: $it")
            viewModel.cityName.value = it
            viewModel.getCurrentWeather()
        }
    }
}
