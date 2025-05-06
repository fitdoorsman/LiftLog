package com.example.liftlog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.ui.screens.HelpScreen
import com.example.liftlog.ui.theme.LiftLogTheme

class HelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiftLogTheme {
                val navController = rememberNavController()
                HelpScreen(navController = navController)
            }
        }
    }
}