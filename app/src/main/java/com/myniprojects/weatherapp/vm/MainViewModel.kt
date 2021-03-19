package com.myniprojects.weatherapp.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myniprojects.weatherapp.model.WeatherResponse
import com.myniprojects.weatherapp.network.ResponseState
import com.myniprojects.weatherapp.repository.MainRepository
import com.myniprojects.weatherapp.utils.Accessibility
import com.myniprojects.weatherapp.utils.next
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel()
{
    private val _weatherResponse: MutableStateFlow<ResponseState<WeatherResponse>> = MutableStateFlow(
        ResponseState.Sleep
    )

    val weatherResponse = _weatherResponse.asStateFlow()

    val cityName = mutableStateOf("")
    val accessibility = mutableStateOf(Accessibility.NORMAL)

    private var searchJob: Job? = null

    fun getCurrentWeather()
    {
        cityName.value = cityName.value.trim()

        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentWeather(cityName.value).collectLatest {
                _weatherResponse.value = it
            }
        }

    }

    fun changeAccessibility()
    {
        accessibility.value = accessibility.value.next
    }

}