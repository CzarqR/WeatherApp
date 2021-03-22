package com.myniprojects.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myniprojects.weatherapp.ui.theme.ThemedPreview

@Composable
fun LoadingScreen()
{
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        LoadingItem()
    }
}


@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
)
{
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview()
{
    ThemedPreview {
        LoadingScreen()
    }
}