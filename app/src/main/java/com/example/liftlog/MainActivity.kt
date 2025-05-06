package com.example.liftlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.*
import com.example.liftlog.data.WorkoutDatabase
import com.example.liftlog.repository.WorkoutRepository
import com.example.liftlog.ui.screens.*
import com.example.liftlog.ui.theme.LiftLogTheme
import com.example.liftlog.viewmodel.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.liftlog.ui.screens.EditWorkoutScreen


class MainActivity : ComponentActivity() {

    private val workoutViewModel: WorkoutViewModel by viewModels {
        WorkoutViewModelFactory(
            WorkoutRepository(
                WorkoutDatabase.getDatabase(applicationContext).workoutDao()
            )
        )
    }

    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            LiftLogTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "dashboard"
                ) {
                    composable("dashboard") {
                        DashboardScreen(
                            viewModel = workoutViewModel,
                            navController = navController
                        )
                    }
                    composable("logWorkout") {
                        LogWorkoutScreen(
                            viewModel = workoutViewModel,
                            navController = navController
                        )
                    }
                    composable("progress") {
                        ProgressTrackerScreen(
                            viewModel = workoutViewModel,
                            navController = navController
                        )
                    }
                    composable("preferences") {
                        PreferencesScreen(
                            navController = navController,
                            themeViewModel = themeViewModel
                        )
                    }
                    composable(
                            route = "edit_workout/{workoutId}",
                            arguments = listOf(navArgument("workoutId") { type = NavType.LongType })
                        ) {
                            val workoutId = it.arguments?.getLong("workoutId") ?: -1L
                            EditWorkoutScreen(
                                workoutId = workoutId,
                                viewModel = workoutViewModel,
                                navController = navController
                            )
                    }
                    composable("help") {
                        HelpScreen(navController = navController)
                    }
                }
            }
        }
    }
}