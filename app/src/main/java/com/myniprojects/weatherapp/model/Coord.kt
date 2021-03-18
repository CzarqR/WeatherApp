package com.myniprojects.weatherapp.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    val lat: Double? = null,
    val lon: Double? = null
)