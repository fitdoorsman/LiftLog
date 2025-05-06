package com.example.liftlog.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.liftlog.viewmodel.WorkoutViewModel
import com.example.liftlog.data.Workout
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogWorkoutScreen(
    viewModel: WorkoutViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    var exerciseName by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val colorScheme = MaterialTheme.colorScheme
    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = colorScheme.surface,
        focusedIndicatorColor = colorScheme.primary,
        unfocusedIndicatorColor = colorScheme.onSurface.copy(alpha = 0.4f),
        cursorColor = colorScheme.primary
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Workout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = { Text("Exercise Name") },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (lbs)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = sets,
                onValueChange = { sets = it },
                label = { Text("Sets") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = reps,
                onValueChange = { reps = it },
                label = { Text("Reps") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") },
                colors = textFieldColors,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val date = SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date())
                    val workout = Workout(
                        id = 0, // required by Room when autoGenerate = true
                        name = exerciseName,
                        date = date,
                        weight = weight.toFloatOrNull() ?: 0f,
                        sets = sets.toIntOrNull() ?: 0,
                        reps = reps.toIntOrNull() ?: 0,
                        notes = notes.ifBlank { null }
                    )
                    viewModel.addWorkout(workout)
                    Toast.makeText(context, "Workout saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()

                    // Clear inputs
                    exerciseName = ""
                    weight = ""
                    sets = ""
                    reps = ""
                    notes = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save Workout")
            }
        }
    }
}