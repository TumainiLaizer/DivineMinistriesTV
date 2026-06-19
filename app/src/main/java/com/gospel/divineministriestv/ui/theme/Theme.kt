package com.gospel.divineministriestv.ui.theme

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = RoyalPurple,
    secondary = RoyalGold,
    tertiary = RoyalGold,
    background = Color.Transparent, // Use gradient in Box
    surface = BrandBackground,
    onPrimary = White,
    onSecondary = BrandBackground,
    onTertiary = BrandBackground,
    onBackground = TextLight,
    onSurface = TextLight,
    surfaceVariant = BrandSurface,
    onSurfaceVariant = TextLight
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalPurple,
    secondary = RoyalGold,
    tertiary = RoyalGold,
    background = Color.Transparent, // Use gradient in Box
    surface = BrandBackground,
    onPrimary = White,
    onSecondary = BrandBackground,
    onTertiary = BrandBackground,
    onBackground = White,
    onSurface = White,
    surfaceVariant = BrandSurface,
    onSurfaceVariant = White
)

@Composable
fun DivineMinistriesTVTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color disabled to maintain brand identity
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(RoyalPurple, BrandBackgroundDark)
                    )
                )
        ) {
            content()
        }
    }
}
