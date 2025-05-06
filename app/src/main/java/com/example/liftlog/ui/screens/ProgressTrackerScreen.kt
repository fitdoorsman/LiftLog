package com.example.liftlog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.liftlog.data.Workout
import com.example.liftlog.viewmodel.WorkoutViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressTrackerScreen(
    viewModel: WorkoutViewModel,
    navController: NavHostController
) {
    val workouts by viewModel.allWorkouts.collectAsState(initial = emptyList())

    // Compute PRs for each unique exercise
    val prs = workouts
        .groupBy { it.name }
        .mapValues { entry -> entry.value.maxByOrNull { it.weight } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Progress Tracker", color = MaterialTheme.colorScheme.onBackground)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Personal Records",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(prs.entries.toList()) { (exercise, workout) ->
                    workout?.let {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "PR: ${it.weight} lbs x ${it.reps} reps on ${it.date}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}