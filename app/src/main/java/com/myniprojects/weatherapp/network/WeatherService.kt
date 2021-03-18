package com.myniprojects.weatherapp.network

import com.myniprojects.weatherapp.model.WeatherResponse
import com.myniprojects.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService
{
    /**
     * Get current weather: https://openweathermap.org/current
     */
    @GET("data/2.5/weather?&units=metric&units=metric&APPID=${Constants.API_KEY}")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
    ): WeatherResponse
}