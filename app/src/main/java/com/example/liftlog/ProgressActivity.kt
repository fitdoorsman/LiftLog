package com.example.liftlog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.data.WorkoutDatabase
import com.example.liftlog.repository.WorkoutRepository
import com.example.liftlog.ui.screens.ChartScreen
import com.example.liftlog.ui.theme.LiftLogTheme
import com.example.liftlog.viewmodel.WorkoutViewModel
import com.example.liftlog.viewmodel.WorkoutViewModelFactory

class ProgressActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels {
        WorkoutViewModelFactory(
            WorkoutRepository(
                WorkoutDatabase.getDatabase(application).workoutDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController() // ✅ Add this
            LiftLogTheme {
                ChartScreen(
                    viewModel = viewModel,
                    navController = navController // ✅ Pass here
                )
            }
        }
    }
}
