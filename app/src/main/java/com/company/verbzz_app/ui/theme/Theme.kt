package com.company.verbzz_app.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = LightGreen,
    primaryVariant = LightPurple,
    onPrimary = Black,

    secondary = TextGray,
    secondaryVariant = LightPurple,
    onSecondary = LightGray,

    surface = Black,
    background = Black
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = DarkYellow,
    primaryVariant = Orange,
    onPrimary = IcyWhite,

    secondary = LightRose,
    secondaryVariant = DarkOrange,
    onSecondary = Black,

    surface = White,
    background = IcyWhite
)

@Composable
fun JetVerbzzTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}