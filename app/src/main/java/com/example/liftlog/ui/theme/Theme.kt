package com.example.liftlog.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ✅ Customize your color palette here
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),     // Light Blue
    secondary = Color(0xFFCE93D8),   // Light Purple
    background = Color(0xFF121212),  // Dark Background
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1565C0),     // Blue
    secondary = Color(0xFF7B1FA2),   // Purple
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)
