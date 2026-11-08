package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AnimeDarkColorScheme = darkColorScheme(
    primary = AnimePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3B0B1A),
    onPrimaryContainer = Color(0xFFFFD9E2),
    secondary = AnimeSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF281142),
    onSecondaryContainer = Color(0xFFEDDCFF),
    tertiary = AnimeAccentCyan,
    onTertiary = Color.Black,
    background = AnimeDarkBg,
    onBackground = TextPrimary,
    surface = AnimeSurface,
    onSurface = TextPrimary,
    surfaceVariant = AnimeCardBg,
    onSurfaceVariant = TextMuted,
    outline = BorderColor,
    error = Color(0xFFFF5252),
    onError = Color.White
)

@Composable
fun AnimeStudioTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AnimeDarkColorScheme,
        typography = Typography,
        content = content
    )
}
