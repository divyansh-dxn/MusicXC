package com.dxn.musicxc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    onPrimary = Color.White,

    secondary = DarkBlack,     // Action bar color
    onSecondary = Color.White, // Action bar text color

    surface = MildBlack,        // Default App color
    onSurface = Color.White     // App Text color

)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    onPrimary = Color.White,

    secondary = Color.White,     // Action bar color
    onSecondary = Color.Black,   // Action bar text color

    surface = Color.White,       // Default App color
    onSurface = Color.Black      // App Text color



    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MusicXCTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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