package com.myniprojects.weatherapp.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.myniprojects.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel()
{
    init
    {
        Timber.d("MainViewModel created. ${hashCode()}")
    }

    fun getCurrentWeather(city: String) = repository.getCurrentWeather(city)

    val cityName = mutableStateOf("")

}