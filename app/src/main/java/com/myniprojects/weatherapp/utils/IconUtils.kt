package com.myniprojects.weatherapp.utils

import androidx.annotation.DrawableRes
import com.myniprojects.weatherapp.R

@DrawableRes
fun getDrawableFromCode(code: String?): Int
{
    return when (code)
    {
        "01d" -> R.drawable.ic_01d
        "01n" -> R.drawable.ic_01n
        "02n", "02d" -> R.drawable.ic_02
        "03n", "03d" -> R.drawable.ic_03
        "04n", "04d" -> R.drawable.ic_04
        "09n", "09d" -> R.drawable.ic_09
        "10n", "10d" -> R.drawable.ic_10
        "11n", "11d" -> R.drawable.ic_11
        "13n", "13d" -> R.drawable.ic_13
        "50n", "50d" -> R.drawable.ic_50
        else -> R.drawable.ic_default
    }
}
