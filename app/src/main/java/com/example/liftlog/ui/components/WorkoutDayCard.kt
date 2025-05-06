package com.example.liftlog.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.liftlog.data.Workout
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun WorkoutDayCard(
    date: String,
    workouts: List<Workout>,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = date,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            workouts.forEach {
                Text(
                    text = "${it.name}: ${it.weight} lbs x ${it.reps} reps",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}