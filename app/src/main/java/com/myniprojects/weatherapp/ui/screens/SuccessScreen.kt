package com.myniprojects.weatherapp.ui.screens

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
import com.myniprojects.weatherapp.utils.Constants
import com.myniprojects.weatherapp.utils.getDateTimeFormatFromSec
import com.myniprojects.weatherapp.utils.getDrawableFromCode
import java.util.*
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.truncate


@Composable
fun SuccessScreen(
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
            CordAndTime(
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
            MainInfo(
                main = weatherResponse.main,
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.medium_margin))
            )
        }

        item {
            SunriseSunsetRow(
                sys = weatherResponse.sys,
                timezone = weatherResponse.timezone
            )
        }

        item {
            WindRow(wind = weatherResponse.wind)
        }

        item {
            CloudsRow(clouds = weatherResponse.clouds)
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
fun CordAndTime(
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
fun WeatherIcon(
    weather: Weather?,
    modifier: Modifier = Modifier,
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
                .fillMaxWidth(0.5f),
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

@Composable
fun MainInfo(
    main: Main?,
    modifier: Modifier = Modifier
)
{

    Column(modifier = modifier.fillMaxWidth()) {

        TemperatureRow(
            temperature = main?.temp,
            feelsLike = main?.feelsLike
        )

        PressureRow(pressure = main?.pressure)

        HumidityRow(humidity = main?.humidity)
    }

}


@Composable
fun RowIconText(
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
fun TemperatureRow(
    temperature: Double?,
    feelsLike: Double?,
)
{
    val temp: String = if (temperature == null)
    {
        stringResource(id = R.string.unknown)
    }
    else
    {
        if (feelsLike == null)
        {
            stringResource(id = R.string.temp_format, "%.1f".format(temperature))
        }
        else
        {
            stringResource(
                id = R.string.temp_format_long,
                "%.1f".format(temperature),
                "%.1f".format(feelsLike)
            )
        }
    }

    RowIconText(
        iconId = R.drawable.ic_temperature,
        text = temp,
    )
}

@Composable
fun PressureRow(
    pressure: Int?
)
{
    val press: String = if (pressure == null)
    {
        stringResource(id = R.string.unknown)
    }
    else
    {
        stringResource(
            id = R.string.pressure_format,
            pressure
        )
    }

    RowIconText(
        iconId = R.drawable.ic_pressure,
        text = press,
    )
}

@Composable
fun HumidityRow(
    humidity: Int?
)
{
    val hum: String = if (humidity == null)
    {
        stringResource(id = R.string.unknown)
    }
    else
    {
        stringResource(
            id = R.string.humidity_format,
            humidity
        )
    }

    RowIconText(
        iconId = R.drawable.ic_drop,
        text = hum,
    )
}


@Composable
fun SunriseSunsetRow(
    sys: Sys?,
    timezone: Long? = null
)
{
    val sunrise = (sys?.sunrise?.plus(
        timezone ?: 0
    ))?.getDateTimeFormatFromSec(Constants.TIME_FORMAT)
    val sunset = (sys?.sunset?.plus(
        timezone ?: 0
    ))?.getDateTimeFormatFromSec(Constants.TIME_FORMAT)

    val text: String = if (sunrise != null || sunset != null)
    {
        val s1 = sunrise ?: stringResource(id = R.string.unknown)
        val s2 = sunset ?: stringResource(id = R.string.unknown)
        stringResource(id = R.string.day_routine_format, s1, s2)
    }
    else
    {
        stringResource(id = R.string.unknown)
    }

    RowIconText(iconId = R.drawable.ic_day_routine, text = text)
}

@Composable
fun WindRow(
    wind: Wind?
)
{
    val s = wind?.speed
    if (s != null)
    {
        RowIconText(
            iconId = R.drawable.ic_wind,
            text = stringResource(id = R.string.wind_format, s),
        )
    }
    else
    {
        RowIconText(
            iconId = R.drawable.ic_wind,
            text = stringResource(id = R.string.unknown),
        )
    }
}

@Composable
fun CloudsRow(
    clouds: Clouds?
)
{
    val c = clouds?.all
    if (c != null)
    {
        RowIconText(
            iconId = R.drawable.ic_clouds,
            text = stringResource(id = R.string.clouds_format, c),
        )
    }
    else
    {
        RowIconText(
            iconId = R.drawable.ic_clouds,
            text = stringResource(id = R.string.unknown),
        )
    }
}

// region previews

@Preview(showBackground = true)
@Composable
fun CordAndTimePreview()
{
    ThemedPreview {
        CordAndTime(
            coord = WEATHER_RESPONSE_SAMPLE.coord,
            time = WEATHER_RESPONSE_SAMPLE.dt,
            timezone = WEATHER_RESPONSE_SAMPLE.timezone
        )
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
fun MainInfoPreview()
{
    ThemedPreview {
        MainInfo(
            WEATHER_RESPONSE_SAMPLE.main
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview()
{
    ThemedPreview {
        SuccessScreen(
            weatherResponse = WEATHER_RESPONSE_SAMPLE
        )
    }
}

// endregion