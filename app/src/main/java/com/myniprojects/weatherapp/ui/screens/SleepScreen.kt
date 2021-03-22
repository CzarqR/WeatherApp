package com.myniprojects.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.ui.theme.ThemedPreview

@Composable
fun SleepScreen()
{
    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        EmptySearchScreen(text = stringResource(id = R.string.search_weather))
    }
}


@Composable
fun EmptySearchScreen(
    text: String,
    modifier: Modifier = Modifier
)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.big_margin))
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_default),
            contentDescription = stringResource(id = R.string.weather_icon),
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.big_margin))
                .size(dimensionResource(id = R.dimen.sleep_icon_size)),
        )

        Text(
            text = text,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SleepScreenPreview()
{
    ThemedPreview {
        SleepScreen()
    }
}