package com.myniprojects.weatherapp.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorScreen(
    exception: Exception
)
{
    Text(text = "Error")
}