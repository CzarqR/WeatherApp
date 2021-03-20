package com.myniprojects.weatherapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessibilityNew
import androidx.compose.material.icons.outlined.Face
import androidx.compose.ui.graphics.vector.ImageVector

enum class Accessibility
{
    NORMAL,
    ELDERLY
}

fun Accessibility.getIcon(): ImageVector = when (this)
{
    Accessibility.NORMAL -> Icons.Outlined.AccessibilityNew
    Accessibility.ELDERLY -> Icons.Outlined.Face
}

inline val <reified T : Enum<T>> T.next: T
    get()
    {
        val values = enumValues<T>()
        return values[(ordinal + 1) % values.size]
    }

