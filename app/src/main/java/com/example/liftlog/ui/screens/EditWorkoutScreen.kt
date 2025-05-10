package com.example.liftlog.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.liftlog.viewmodel.WorkoutViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWorkoutScreen(
    workoutId: Long,
    viewModel: WorkoutViewModel,
    navController: NavController
) {
    Log.d("DEBUG", "EditWorkoutScreen loaded for ID: $workoutId")

    val workoutState = viewModel.getWorkoutById(workoutId).collectAsState(initial = null)
    val workout = workoutState.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Navigate back if workout is deleted
    LaunchedEffect(workoutId) {
        viewModel.getWorkoutById(workoutId).collect { result ->
            if (result == null) {
                Log.d("DEBUG", "Exercise not found or deleted. Navigating back.")
                navController.popBackStack()
            }
        }
    }

    if (workout == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading exercise...")
        }
        return
    }

    // 🗓 Format date with year, regardless of input format
    val formattedDate = try {
        Log.d("DEBUG", "Raw workout.date = ${workout.date}")
        val inputFormatter = DateTimeFormatter.ofPattern("MMM dd")
        val parsed = LocalDate.parse(workout.date, inputFormatter).withYear(LocalDate.now().year)
        parsed.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    } catch (e1: Exception) {
        try {
            val parsed = LocalDate.parse(workout.date) // ISO format fallback
            parsed.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
        } catch (e2: Exception) {
            Log.e("DateParseError", "Failed to parse workout.date: ${workout.date}", e2)
            workout.date
        }
    }

    var name by rememberSaveable { mutableStateOf(workout.name) }
    var weight by rememberSaveable { mutableStateOf(workout.weight.toString()) }
    var sets by rememberSaveable { mutableStateOf(workout.sets.toString()) }
    var reps by rememberSaveable { mutableStateOf(workout.reps.toString()) }
    var notes by rememberSaveable { mutableStateOf(workout.notes ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Editing: ${workout.name}",
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "Date: $formattedDate",
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }
                },
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
                value = name,
                onValueChange = { name = it },
                label = { Text("Exercise Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (lbs)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = sets,
                onValueChange = { sets = it },
                label = { Text("Sets") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = reps,
                onValueChange = { reps = it },
                label = { Text("Reps") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        val updatedWorkout = workout.copy(
                            name = name,
                            weight = weight.toFloatOrNull() ?: 0f,
                            sets = sets.toIntOrNull() ?: 0,
                            reps = reps.toIntOrNull() ?: 0,
                            notes = notes.ifBlank { null }
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
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Delete Exercise?") },
                    text = { Text("Are you sure you want to delete this exercise? This cannot be undone.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDeleteDialog = false
                            scope.launch {
                                viewModel.deleteWorkout(workout)
                                snackbarHostState.showSnackbar("Exercise deleted")
                            }
                        }) {
                            Text("Delete", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
