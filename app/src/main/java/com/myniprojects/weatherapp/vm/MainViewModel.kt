package com.myniprojects.weatherapp.vm

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.weatherapp.model.WeatherResponse
import com.myniprojects.weatherapp.network.ResponseState
import com.myniprojects.weatherapp.repository.MainRepository
import com.myniprojects.weatherapp.utils.Accessibility
import com.myniprojects.weatherapp.utils.Constants
import com.myniprojects.weatherapp.utils.next
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel()
{
    private val _weatherResponse: MutableStateFlow<ResponseState<WeatherResponse>> = MutableStateFlow(
        ResponseState.Sleep
    )

    val weatherResponse = _weatherResponse.asStateFlow()

    val cityName = mutableStateOf("")
    val accessibility = mutableStateOf(Accessibility.NORMAL)

    private var searchJob: Job? = null

    /**
     * Load last searched city
     * If there was saved any city load weather
     */

    fun getCurrentWeather()
    {
        cityName.value = cityName.value.trim()

        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentWeather(cityName.value).collectLatest {

                if (it is ResponseState.Success)
                {
                    val c = it.data.sys?.country
                    val ct = if (c != null)
                    {
                        "${it.data.name}, $c"
                    }
                    else
                    {
                        it.data.name
                    }

                    cityName.value = ct

                    /**
                     * Search was successful
                     * Save city name
                     */
                    with(sharedPreferences.edit()) {
                        putString(Constants.SH_LAST_CITY_KEY, ct)
                        apply()
                    }
                }

                _weatherResponse.value = it
            }
        }
    }


    fun changeAccessibility()
    {
        accessibility.value = accessibility.value.next
    }

}