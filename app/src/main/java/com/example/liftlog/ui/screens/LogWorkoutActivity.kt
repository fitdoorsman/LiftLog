package com.example.liftlog.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.liftlog.data.WorkoutDatabase
import com.example.liftlog.repository.WorkoutRepository
import com.example.liftlog.viewmodel.WorkoutViewModel
import com.example.liftlog.viewmodel.WorkoutViewModelFactory
import com.example.liftlog.ui.theme.LiftLogTheme
import androidx.navigation.compose.rememberNavController

/**
 * Activity to handle workout logging input.
 */
class LogWorkoutActivity : ComponentActivity() {
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
            val navController = rememberNavController()
            LiftLogTheme {
                LogWorkoutScreen(
                    viewModel = viewModel,
                    navController = navController
                )
                }
            }
        }
    }
