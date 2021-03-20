package com.myniprojects.weatherapp.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Long.getDateTimeFormatFromSec(
    format: String = Constants.DATE_TIME_FORMAT
): String
{

    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
    {
        Instant.ofEpochMilli(this * 1000)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .format(
                DateTimeFormatter.ofPattern(
                    format,
                    Locale.getDefault()
                )
            )
    }
    else
    {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        val calendar = Calendar.getInstance().apply { timeInMillis = this@getDateTimeFormatFromSec }
        return formatter.format(calendar.time)
    }
}