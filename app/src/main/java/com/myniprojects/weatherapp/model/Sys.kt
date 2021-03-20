package com.myniprojects.weatherapp.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sys(
    val country: String? = null,
    val id: Int? = null,
    val message: Double? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val type: Int? = null
)