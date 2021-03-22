package com.myniprojects.weatherapp.ui.screens.success

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.myniprojects.weatherapp.model.WEATHER_RESPONSE_SAMPLE
import com.myniprojects.weatherapp.model.Weather
import com.myniprojects.weatherapp.model.WeatherResponse
import com.myniprojects.weatherapp.ui.theme.ThemedPreview
import com.myniprojects.weatherapp.utils.Accessibility
import com.myniprojects.weatherapp.utils.getDrawableFromCode
import java.util.*


@Composable
fun SuccessScreen(
    weatherResponse: WeatherResponse,
    accessibility: Accessibility
)
{
    Crossfade(targetState = accessibility) { access ->
        when (access)
        {
            Accessibility.NORMAL -> SuccessScreenNormal(weatherResponse = weatherResponse)
            Accessibility.ELDERLY -> SuccessScreenElderly(weatherResponse = weatherResponse)
        }
    }
}

@Composable
fun WeatherIcon(
    weather: Weather?,
    modifier: Modifier = Modifier,
    widthPercentage: Float = 0.5f
)
{
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = getDrawableFromCode(weather?.icon)),
            contentDescription = stringResource(id = R.string.weather_icon),
            modifier = Modifier
                .fillMaxWidth(widthPercentage),
        )
        weather?.description?.let { desc ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.medium_margin)),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                text = desc.capitalize(Locale.getDefault())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherIconPreview()
{
    ThemedPreview {
        WeatherIcon(
            weather = WEATHER_RESPONSE_SAMPLE.weather?.get(0)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenNPreview()
{
    ThemedPreview {
        SuccessScreen(
            weatherResponse = WEATHER_RESPONSE_SAMPLE,
            accessibility = Accessibility.NORMAL
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenEPreview()
{
    ThemedPreview {
        SuccessScreen(
            weatherResponse = WEATHER_RESPONSE_SAMPLE,
            accessibility = Accessibility.ELDERLY
        )
    }
}
