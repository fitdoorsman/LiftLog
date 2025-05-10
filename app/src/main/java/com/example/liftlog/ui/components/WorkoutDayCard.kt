package com.example.liftlog.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.liftlog.data.Workout

@Composable
fun WorkoutDayCard(
    date: String,
    workouts: List<Workout>,
    useKg: Boolean,
    prWorkoutIds: Set<Long>,
    onWorkoutClick: (Workout) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = date,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            val shownPRsToday = mutableSetOf<String>() // 🏆 Track first PR shown per name

            workouts.forEachIndexed { index, workout ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Log.d("WorkoutClick", "Workout clicked: ${workout.id}")
                            onWorkoutClick(workout)
                        }
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = workout.name,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )

                        val isPR = workout.id in prWorkoutIds
                        val alreadyShown = workout.name in shownPRsToday

                        if (isPR && !alreadyShown) {
                            Icon(
                                imageVector = Icons.Filled.MilitaryTech,
                                contentDescription = "Personal Record",
                                tint = Color(0xFFFFD700)
                            )
                            shownPRsToday.add(workout.name)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${workout.weight} ${if (useKg) "kg" else "lbs"} - ${workout.sets} sets x ${workout.reps} reps",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (index != workouts.lastIndex) {
                    Divider(modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}
