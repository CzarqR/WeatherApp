package com.myniprojects.weatherapp.repository

import com.myniprojects.weatherapp.model.WeatherResponse
import com.myniprojects.weatherapp.network.ResponseState
import com.myniprojects.weatherapp.network.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val weatherService: WeatherService
)
{
    fun getCurrentWeather(city: String): Flow<ResponseState<WeatherResponse>> =
            flow {
                emit(ResponseState.Loading)
                try
                {
                    val weatherResponse = weatherService.getCurrentWeather(city)
                    emit(ResponseState.Success(weatherResponse))
                }
                catch (e: IOException)
                {
                    emit(ResponseState.Error(e))
                }
                catch (e: HttpException)
                {
                    emit(ResponseState.Error(e))
                }
            }
}