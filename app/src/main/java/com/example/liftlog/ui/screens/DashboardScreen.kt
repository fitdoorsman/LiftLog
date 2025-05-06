package com.example.liftlog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.liftlog.ui.components.WorkoutDayCard
import com.example.liftlog.viewmodel.WorkoutViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: WorkoutViewModel,
    navController: NavController
) {
    val workouts = viewModel.allWorkouts.collectAsState(initial = emptyList()).value
    val groupedWorkouts = workouts.groupBy { it.date }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Workout Dashboard",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    groupedWorkouts.forEach { (date, workoutsForDate) ->
                        item {
                            WorkoutDayCard(
                                date = date,
                                workouts = workoutsForDate,
                                onClick = {
                                    if (workoutsForDate.isNotEmpty()) {
                                        navController.navigate("edit_workout/${workoutsForDate[0].id}")
                                    }
                                }
                            )
                        }
                    }
                }

                // Buttons
                Button(
                    onClick = { navController.navigate("logWorkout") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Start New Workout")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.navigate("preferences") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7B1FA2))
                    ) {
                        Text("Settings")
                    }
                    Button(
                        onClick = { navController.navigate("help") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
                    ) {
                        Text("Help")
                    }
                    Button(
                        onClick = { navController.navigate("progress") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF6C00))
                    ) {
                        Text("Progress Tracker")
                    }
                }
            }
        }
    )
}