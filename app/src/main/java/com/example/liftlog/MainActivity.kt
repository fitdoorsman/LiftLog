package com.example.liftlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.liftlog.data.WorkoutDatabase
import com.example.liftlog.repository.WorkoutRepository
import com.example.liftlog.ui.screens.*
import com.example.liftlog.ui.theme.LiftLogTheme
import com.example.liftlog.viewmodel.*

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
            LiftLogTheme {
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
                    composable("preferences") {
                        PreferencesScreen(
                            navController = navController,
                            themeViewModel = themeViewModel
                        )
                    }
                    composable("help") {
                        HelpScreen(navController = navController)
                    }
                    composable("chart") {
                        ChartScreen(
                            viewModel = workoutViewModel,
                            navController = navController
                        )
                    }
                    composable(
                        route = "edit_workout/{workoutId}",
                        arguments = listOf(navArgument("workoutId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val workoutId = backStackEntry.arguments?.getLong("workoutId") ?: -1L
                        EditWorkoutScreen(
                            workoutId = workoutId,
                            viewModel = workoutViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
