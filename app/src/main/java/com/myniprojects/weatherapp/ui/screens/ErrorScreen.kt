package com.myniprojects.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myniprojects.weatherapp.R
import com.myniprojects.weatherapp.ui.theme.ThemedPreview
import com.myniprojects.weatherapp.utils.Constants
import com.myniprojects.weatherapp.vm.MainViewModel

@Composable
fun ErrorScreen(
    exception: Exception
)
{
    val mainViewModel: MainViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        ErrorItem(
            message = exception.localizedMessage ?: exception.message
            ?: stringResource(id = R.string.something_went_wrong),
            onClickRetry = mainViewModel::getCurrentWeather
        )
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
)
{
    val m = if (message == Constants.HTTP_404) stringResource(id = R.string.city_not_found) else message

    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = m,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.error
        )
        IconButton(
            onClick = onClickRetry,
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.medium_margin))
        ) {
            Icon(
                imageVector = Icons.Outlined.Autorenew,
                contentDescription = stringResource(id = R.string.city_icon)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorItemPrev()
{
    ThemedPreview {
        Column {
            ErrorItem(
                message = "It is a long established fact that a reader will be distracted.",
                onClickRetry = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            ErrorItem(
                message = "The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here',",
                onClickRetry = {}
            )
        }
    }
}