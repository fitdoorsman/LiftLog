package com.example.liftlog.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.liftlog.viewmodel.ThemeViewModel
import com.example.liftlog.viewmodel.ThemeViewModelFactory

@Composable
fun LiftLogTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val themeViewModel: ThemeViewModel = viewModel(factory = ThemeViewModelFactory(context))
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState(initial = false)

    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LiftLogTypography,
        shapes = LiftLogShapes,
        content = content
    )
}
