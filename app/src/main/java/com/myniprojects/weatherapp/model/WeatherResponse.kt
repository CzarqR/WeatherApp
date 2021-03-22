package com.myniprojects.weatherapp.model


import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.utils.Constants
import com.myniprojects.weatherapp.utils.getDateTimeFormatFromSec
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Long = 0,
    val id: Int? = null,
    val main: Main? = null,
    val name: String = "",
    val sys: Sys? = null,
    val timezone: Long? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind? = null
)

@Composable
fun getTempValue(temperature: Double?, feelsLike: Double?): String = if (temperature == null)
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

@Composable
fun getPressureValue(pressure: Int?) = if (pressure == null)
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

@Composable
fun getHumidityValue(humidity: Int?) = if (humidity == null)
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

@Composable
fun getSunriseSunsetValue(
    sys: Sys?,
    timezone: Long?
): String
{
    val sunrise = (sys?.sunrise?.plus(
        timezone ?: 0
    ))?.getDateTimeFormatFromSec(Constants.TIME_FORMAT)
    val sunset = (sys?.sunset?.plus(
        timezone ?: 0
    ))?.getDateTimeFormatFromSec(Constants.TIME_FORMAT)

    return if (sunrise != null || sunset != null)
    {
        val s1 = sunrise ?: stringResource(id = R.string.unknown)
        val s2 = sunset ?: stringResource(id = R.string.unknown)
        stringResource(id = R.string.day_routine_format, s1, s2)
    }
    else
    {
        stringResource(id = R.string.unknown)
    }
}

@Composable
fun getWindValue(wind: Wind?) = if (wind?.speed != null)
{
    stringResource(id = R.string.wind_format, wind.speed)
}
else
{
    stringResource(id = R.string.unknown)
}

@Composable
fun getCloudsValue(clouds: Clouds?) = if (clouds?.all != null)
{
    stringResource(id = R.string.clouds_format, clouds.all)
}
else
{
    stringResource(id = R.string.unknown)
}

val WEATHER_RESPONSE_SAMPLE = WeatherResponse(
    coord = Coord(
        lon = -80.6081,
        lat = 28.0836
    ),
    clouds = Clouds(
        all = 20
    ),
    weather = listOf(
        Weather(
            description = "few clouds",
            icon = "02d",
        )
    ),
    main = Main(
        temp = 18.31,
        feelsLike = 19.93,
        pressure = 1032,
        humidity = 81
    ),
    wind = Wind(
        speed = 6.31
    ),
    sys = Sys(
        country = "US",
        sunrise = 1616325891,
        sunset = 1616369647
    ),
    dt = 1616349311,
    timezone = -14400,
    name = "Melbourne"
)