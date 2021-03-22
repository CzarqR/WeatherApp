package com.myniprojects.weatherapp.ui.screens.success

import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.model.*
import com.myniprojects.weatherapp.ui.theme.ThemedPreview
import com.myniprojects.weatherapp.utils.getDateTimeFormatFromSec
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.truncate

@Composable
fun SuccessScreenNormal(
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
            CordAndTimeNormal(
                coord = weatherResponse.coord,
                time = weatherResponse.dt,
                timezone = weatherResponse.timezone
            )
        }

        item {
            WeatherIcon(
                weather = weatherResponse.weather?.getOrNull(0),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.medium_margin))
            )
        }

        item {
            TemperatureRowNormal(
                temperature = weatherResponse.main?.temp,
                feelsLike = weatherResponse.main?.feelsLike
            )
        }

        item {
            PressureRowNormal(pressure = weatherResponse.main?.pressure)
        }

        item {
            HumidityRowNormal(humidity = weatherResponse.main?.humidity)
        }

        item {
            SunriseSunsetRowNormal(
                sys = weatherResponse.sys,
                timezone = weatherResponse.timezone
            )
        }

        item {
            WindRowNormal(wind = weatherResponse.wind)
        }

        item {
            CloudsRowNormal(clouds = weatherResponse.clouds)
        }
    }
}

@Composable
fun Time(
    time: Long,
    timezone: Long?,
    modifier: Modifier = Modifier
)
{

    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.medium_margin),
                vertical = dimensionResource(id = R.dimen.small_margin),
            ),
        text = (time.plus(timezone ?: 0)).getDateTimeFormatFromSec(),
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)

    )
}


@Composable
fun LongLat(
    coord: Coord?,
    modifier: Modifier = Modifier

)
{
    val long = coord?.lon
    val lat = coord?.lat

    if (long != null && lat != null)
    {
        val latD = round(lat).toInt()
        val latM = round((truncate(abs(lat)) * 60 / 100)).toInt()
        val latDir: String = if (lat > 0) "N" else if (lat < 0) "S" else ""

        val longD = round(long).toInt()
        val longM = round((truncate(abs(long)) * 60 / 100)).toInt()
        val longDir: String = if (long > 0) "E" else if (long < 0) "W" else ""


        Text(
            modifier = modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.medium_margin),
                    vertical = dimensionResource(id = R.dimen.small_margin),
                ),
            text = stringResource(
                id = R.string.coord_format,
                latD,
                latM,
                latDir,
                longD,
                longM,
                longDir
            ),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)

        )

    }
}

@Composable
fun CordAndTimeNormal(
    coord: Coord?,
    time: Long,
    timezone: Long?,
    modifier: Modifier = Modifier
)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LongLat(
            coord = coord
        )

        Time(
            time = time,
            timezone = timezone,
            modifier = modifier.weight(1f),
        )
    }
}


@Composable
fun RowIconTextNormal(
    @DrawableRes iconId: Int,
    text: String,
    modifier: Modifier = Modifier,
    @DimenRes iconSizeId: Int = R.dimen.base_icon_size,
    textStyle: TextStyle = MaterialTheme.typography.h6,
)
{
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = stringResource(id = R.string.weather_icon),
            modifier = Modifier
                .size(dimensionResource(iconSizeId))
                .padding(dimensionResource(id = R.dimen.medium_margin)),
        )


        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(id = R.dimen.medium_margin)),
            text = text,
            style = textStyle,
        )
    }
}


@Composable
fun TemperatureRowNormal(
    temperature: Double?,
    feelsLike: Double?,
)
{
    RowIconTextNormal(
        iconId = R.drawable.ic_temperature,
        text = getTempValue(
            temperature = temperature,
            feelsLike = feelsLike
        ),
    )
}

@Composable
fun PressureRowNormal(
    pressure: Int?
)
{
    RowIconTextNormal(
        iconId = R.drawable.ic_pressure,
        text = getPressureValue(pressure = pressure),
    )
}

@Composable
fun HumidityRowNormal(
    humidity: Int?
)
{
    RowIconTextNormal(
        iconId = R.drawable.ic_drop,
        text = getHumidityValue(humidity = humidity),
    )
}


@Composable
fun SunriseSunsetRowNormal(
    sys: Sys?,
    timezone: Long? = null
)
{
    RowIconTextNormal(
        iconId = R.drawable.ic_day_routine,
        text = getSunriseSunsetValue(sys = sys, timezone = timezone)
    )
}

@Composable
fun WindRowNormal(
    wind: Wind?
)
{
    RowIconTextNormal(
        iconId = R.drawable.ic_wind,
        text = getWindValue(wind = wind)
    )
}

@Composable
fun CloudsRowNormal(
    clouds: Clouds?
)
{
    RowIconTextNormal(
        iconId = R.drawable.ic_clouds,
        text = getCloudsValue(clouds = clouds),
    )
}

// region previews

@Preview(showBackground = true)
@Composable
fun CordAndTimePreview()
{
    ThemedPreview {
        CordAndTimeNormal(
            coord = WEATHER_RESPONSE_SAMPLE.coord,
            time = WEATHER_RESPONSE_SAMPLE.dt,
            timezone = WEATHER_RESPONSE_SAMPLE.timezone
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RowNormalPreview()
{
    ThemedPreview {
        HumidityRowNormal(
            humidity = WEATHER_RESPONSE_SAMPLE.main?.humidity,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RowNormalNullPreview()
{
    ThemedPreview {
        HumidityRowNormal(
            humidity = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenNormalPreview()
{
    ThemedPreview {
        SuccessScreenNormal(
            weatherResponse = WEATHER_RESPONSE_SAMPLE,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SuccessScreenElderlyPreview()
{
    ThemedPreview {
        SuccessScreenElderly(
            weatherResponse = WEATHER_RESPONSE_SAMPLE,
        )
    }
}

// endregion