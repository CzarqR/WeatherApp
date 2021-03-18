package com.myniprojects.weatherapp.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.ui.theme.WeatherAppTheme
import com.myniprojects.weatherapp.ui.theme.getGradientBottom
import com.myniprojects.weatherapp.ui.theme.getGradientTop
import com.myniprojects.weatherapp.vm.MainViewModel
import com.skydoves.landscapist.glide.GlideImage
import timber.log.Timber

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
)
{
    val isDark = isSystemInDarkTheme()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(

                Brush.verticalGradient(
                    listOf(
                        getGradientTop(isDark), getGradientBottom(isDark)
                    ),
                )
            ),
        contentAlignment = Alignment.BottomEnd
    )
    {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                CityInput(
                    mainViewModel = viewModel
                )
            }

            item {
                GlideImage(
                    imageModel = "https://openweathermap.org/img/wn/10d@4x.png",
                    modifier = Modifier.size(100.dp),
                    circularRevealedEnabled = true,
                    circularRevealedDuration = 1000,
                )
            }

        }

        FloatingActionButton(
            modifier = Modifier
                .padding(
                    end = dimensionResource(id = R.dimen.big_margin),
                    bottom = dimensionResource(id = R.dimen.big_margin)
                ),
            onClick = {
                Timber.d("Accessibility clicked")
            },
        ) {
            Icon(
                imageVector = Icons.Outlined.AccessibilityNew,
                contentDescription = stringResource(id = R.string.accessibility_icon)
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
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview()
{
    WeatherAppTheme {

    }
}
