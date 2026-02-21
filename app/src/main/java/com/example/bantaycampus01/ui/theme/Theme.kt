package com.example.finalproject.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.Typography
import com.example.bantaycampus01.ui.theme.White

private val DarkColorScheme = darkColorScheme(
    primary = DarkGrayBlue,
    onPrimary = White,
    background = DarkGrayBlue,
    onBackground = White,
    surface = DarkGrayBlue,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = DarkGrayBlue,
    onPrimary = White,
    background = White,
    onBackground = DarkGrayBlue,
    surface = White,
    onSurface = DarkGrayBlue
)

@Composable
fun FinalProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
