package com.myniprojects.weatherapp.ui.screens

import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.model.Main
import com.myniprojects.weatherapp.model.Sys
import com.myniprojects.weatherapp.model.Weather
import com.myniprojects.weatherapp.model.WeatherResponse
import com.myniprojects.weatherapp.utils.Constants
import com.myniprojects.weatherapp.utils.getDateTimeFormatFromSec
import com.myniprojects.weatherapp.utils.getDrawableFromCode
import com.myniprojects.weatherapp.vm.MainViewModel
import timber.log.Timber
import java.util.*


@Composable
fun SuccessScreen(
    weatherResponse: WeatherResponse
)
{
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            Time(time = weatherResponse.dt)
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
            SunriseSunset(sys = weatherResponse.sys)
        }
    }
}


@Composable
fun CityInput(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
)
{
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.medium_margin))
            .fillMaxWidth(),
        value = mainViewModel.cityName.value,
        onValueChange = mainViewModel.cityName.component2(),
        label = { Text(stringResource(id = R.string.city_name)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.LocationCity,
                contentDescription = stringResource(id = R.string.city_icon)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        singleLine = true,
        keyboardActions = KeyboardActions(

            onSearch = {
                Timber.d("Search clicked")
                focusManager.clearFocus()
                mainViewModel.getCurrentWeather()
            }
        )
    )
}

@Composable
fun Time(
    time: Long,
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
        text = time.getDateTimeFormatFromSec(),
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)

    )
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
fun SunriseSunset(
    sys: Sys?
)
{
    val sunrise = sys?.sunrise?.getDateTimeFormatFromSec(Constants.TIME_FORMAT)
    val sunset = sys?.sunset?.getDateTimeFormatFromSec(Constants.TIME_FORMAT)

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

// region previews

@Preview(showBackground = true)
@Composable
fun WeatherIconPreview()
{
    MaterialTheme {
        WeatherIcon(
            weather = Weather(
                main = "Clear",
                description = "clear sky",
                id = 800,
                icon = "01d"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainInfoPreview()
{
    MaterialTheme {
        MainInfo(
            Main(
                temp = 14.74,
                feelsLike = 15.62,
                pressure = 1019,
                humidity = 94
            )
        )
    }
}


// endregion