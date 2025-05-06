package com.example.liftlog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavController
import com.example.liftlog.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWorkoutScreen(
    workoutId: Long,
    viewModel: WorkoutViewModel,
    navController: NavController
) {
    val workoutState = viewModel.getWorkoutById(workoutId).collectAsState(initial = null)
    val workout = workoutState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (workout == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading workout...")
        }
        return
    }

    val name = remember { mutableStateOf(workout.name) }
    val weight = remember { mutableStateOf(workout.weight.toString()) }
    val reps = remember { mutableStateOf(workout.reps.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit Workout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text(text = "Exercise Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = weight.value,
                onValueChange = { weight.value = it },
                label = { Text(text = "Weight (lbs)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = reps.value,
                onValueChange = { reps.value = it },
                label = { Text(text = "Reps") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        val updatedWorkout = workout.copy(
                            name = name.value,
                            weight = weight.value.toFloatOrNull() ?: 0f,
                            reps = reps.value.toIntOrNull() ?: 0
                        )
                        scope.launch {
                            viewModel.updateWorkout(updatedWorkout)
                            snackbarHostState.showSnackbar("Workout updated")
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Save Changes")
                }

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.deleteWorkout(workout)
                            snackbarHostState.showSnackbar("Workout deleted")
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}