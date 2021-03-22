package com.myniprojects.weatherapp.ui.screens.success

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.model.*
import com.myniprojects.weatherapp.ui.theme.ThemedPreview


@Composable
fun SuccessScreenElderly(
    weatherResponse: WeatherResponse
)
{
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = dimensionResource(id = R.dimen.bottom_list_padding)
        )
    ) {

        item {
            WeatherIcon(
                weather = weatherResponse.weather?.getOrNull(0),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.medium_margin)),
                widthPercentage = 0.3f
            )
        }

        item {
            TemperatureRowElderly(
                temperature = weatherResponse.main?.temp,
                feelsLike = weatherResponse.main?.feelsLike
            )
        }

        item {
            PressureRowElderly(
                pressure = weatherResponse.main?.pressure,
            )
        }

        item {
            HumidityRowElderly(humidity = weatherResponse.main?.humidity)
        }

        item {
            SunriseSunsetRowElderly(sys = weatherResponse.sys, timezone = weatherResponse.timezone)
        }

        item {
            WindRowElderly(wind = weatherResponse.wind)
        }

        item {
            CloudsRowElderly(clouds = weatherResponse.clouds)
        }

    }
}

@Composable
fun ElderlyRow(
    @StringRes title: Int,
    value: String,
    modifier: Modifier = Modifier
)
{
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Divider(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.medium_margin),
                    vertical = dimensionResource(id = R.dimen.big_margin)
                )
                .fillMaxWidth(),
            thickness = dimensionResource(id = R.dimen.thick_divider)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.h3,
        )

        Text(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium_margin)),
            text = stringResource(id = title),
            style = MaterialTheme.typography.h4.copy(fontSize = 28.sp)
        )


    }
}


@Composable
fun TemperatureRowElderly(
    temperature: Double?,
    feelsLike: Double?,
)
{
    ElderlyRow(
        title = R.string.temperature_perceived,
        value = getTempValue(
            temperature = temperature,
            feelsLike = feelsLike
        ),
    )
}

@Composable
fun PressureRowElderly(
    pressure: Int?,
)
{
    ElderlyRow(
        title = R.string.pressure,
        value = getPressureValue(
            pressure = pressure,
        )
    )
}


@Composable
fun HumidityRowElderly(
    humidity: Int?,
)
{
    ElderlyRow(
        title = R.string.humidity,
        value = getHumidityValue(
            humidity = humidity,
        )
    )
}

@Composable
fun SunriseSunsetRowElderly(
    sys: Sys?,
    timezone: Long? = null
)
{
    ElderlyRow(
        title = R.string.sunrise_sunset,
        value = getSunriseSunsetValue(
            sys = sys,
            timezone = timezone
        )
    )
}


@Composable
fun WindRowElderly(
    wind: Wind?,
)
{
    ElderlyRow(
        title = R.string.wind,
        value = getWindValue(
            wind = wind,
        )
    )
}

@Composable
fun CloudsRowElderly(
    clouds: Clouds?,
)
{
    ElderlyRow(
        title = R.string.cloudiness,
        value = getCloudsValue(
            clouds = clouds,
        )
    )
}


@Preview(showBackground = true)
@Composable
fun MainInfoPreviewElderly()
{
    ThemedPreview {
        TemperatureRowElderly(
            WEATHER_RESPONSE_SAMPLE.main?.temp,
            WEATHER_RESPONSE_SAMPLE.main?.feelsLike,
        )
    }
}