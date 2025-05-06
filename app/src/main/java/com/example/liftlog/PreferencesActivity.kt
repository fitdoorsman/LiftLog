package com.example.liftlog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.ui.screens.PreferencesScreen
import com.example.liftlog.ui.theme.LiftLogTheme
import com.example.liftlog.viewmodel.ThemeViewModel
import com.example.liftlog.viewmodel.ThemeViewModelFactory

class PreferencesActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LiftLogTheme {
                val navController = rememberNavController()
                PreferencesScreen(
                    navController = navController,
                    themeViewModel = themeViewModel
                )
            }
        }
    }
}