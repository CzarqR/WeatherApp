package com.myniprojects.weatherapp.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Long = 0,
    val id: Int? = null,
    val main: Main? = null,
    val name: String = "",
    val sys: Sys? = null,
    val timezone: Long? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null
)

val WEATHER_RESPONSE_SAMPLE = WeatherResponse(
    clouds = Clouds(
        all = 20
    ),
    weather = listOf(
        Weather(
            description = "few clouds",
            icon = "02d",
        )
    ),
    main = Main(
        temp = 18.31,
        feelsLike = 19.93,
        pressure = 1032,
        humidity = 81
    ),
    wind = Wind(
        speed = 6.31
    ),
    sys = Sys(
        country = "US",
        sunrise = 1616325891,
        sunset = 1616369647
    ),
    dt = 1616349311,
    timezone = -14400,
    name = "Melbourne"
)