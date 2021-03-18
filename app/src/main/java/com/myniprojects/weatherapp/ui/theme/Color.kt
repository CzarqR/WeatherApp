package com.myniprojects.weatherapp.ui.theme

import androidx.compose.ui.graphics.Color

val DarkSecondary = Color(0xFFE1F5FE)
val LightSecondary = Color(0xFFB3E5FC)


val GradientBottomLight = Color(0xFFfaaca8)
val GradientTopLight = Color(0xFFddd6f3)

val GradientBottomNight = Color(0xFF000428)
val GradientTopNight = Color(0xFF004e92)

fun getGradientTop(isSystemDarkTheme: Boolean) =
        if (isSystemDarkTheme) GradientTopNight else GradientTopLight

fun getGradientBottom(isSystemDarkTheme: Boolean) =
        if (isSystemDarkTheme) GradientBottomNight else GradientBottomLight