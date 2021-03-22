package com.myniprojects.weatherapp.ui.screens


import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.model.WeatherResponse
import com.myniprojects.weatherapp.network.ResponseState
import com.myniprojects.weatherapp.ui.screens.success.SuccessScreen
import com.myniprojects.weatherapp.ui.theme.getGradientBottom
import com.myniprojects.weatherapp.ui.theme.getGradientTop
import com.myniprojects.weatherapp.utils.Accessibility
import com.myniprojects.weatherapp.utils.getIcon
import com.myniprojects.weatherapp.vm.MainViewModel
import timber.log.Timber

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
)
{
    val isDark = isSystemInDarkTheme()

    val networkResponse: ResponseState<WeatherResponse> by mainViewModel.weatherResponse.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        getGradientTop(isDark), getGradientBottom(isDark)
                    ),
                )
            ),
    )
    {

        CityInput(
            mainViewModel = mainViewModel
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.BottomEnd
        ) {
            StateBody(
                response = networkResponse,
                accessibility = mainViewModel.accessibility.value
            )

            AccessibilityButton(
                accessibility = mainViewModel.accessibility.value,
                onClick = mainViewModel::changeAccessibility
            )
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
            .fillMaxWidth()
            .animateContentSize(),
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
                if (mainViewModel.cityName.value.isNotBlank())
                {
                    focusManager.clearFocus()
                    mainViewModel.getCurrentWeather()
                }
            }
        ),
        textStyle = when (mainViewModel.accessibility.value)
        {
            Accessibility.NORMAL ->
            {
                MaterialTheme.typography.body1
            }
            Accessibility.ELDERLY ->
            {
                MaterialTheme.typography.h4.copy(fontSize = 28.sp)
            }
        }
    )
}

@Composable
fun StateBody(
    response: ResponseState<WeatherResponse>,
    accessibility: Accessibility
)
{
    Crossfade(targetState = response) {
        when (it)
        {
            is ResponseState.Success -> SuccessScreen(
                weatherResponse = it.data,
                accessibility = accessibility
            )
            is ResponseState.Error -> ErrorScreen(exception = it.exception)
            ResponseState.Loading -> LoadingScreen()
            ResponseState.Sleep -> SleepScreen()
        }
    }

}

@Composable
fun AccessibilityButton(
    accessibility: Accessibility,
    onClick: () -> Unit
)
{
    FloatingActionButton(
        modifier = Modifier
            .padding(
                end = dimensionResource(id = R.dimen.big_margin),
                bottom = dimensionResource(id = R.dimen.big_margin)
            ),
        onClick = {
            onClick()
        },
        backgroundColor = getGradientTop(isSystemInDarkTheme())
    ) {
        Icon(
            imageVector = accessibility.getIcon(),
            contentDescription = stringResource(id = R.string.accessibility_icon)
        )
    }
}


