package com.example.liftlog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.liftlog.data.Workout

@Composable
fun WorkoutList(workouts: List<Workout>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        workouts.forEach { workout ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Exercise: ${workout.name}")
                    Text("Weight: ${workout.weight} lbs")
                    Text("Sets: ${workout.sets}")
                    Text("Reps: ${workout.reps}")
                }
            }
        }
    }
}
