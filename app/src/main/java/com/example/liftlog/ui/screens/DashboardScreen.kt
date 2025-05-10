package com.example.liftlog.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.liftlog.ui.components.WorkoutDayCard
import com.example.liftlog.viewmodel.WorkoutViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: WorkoutViewModel,
    navController: NavController
) {
    val workouts = viewModel.allWorkouts.collectAsState(initial = emptyList()).value
    val context = LocalContext.current
    val useKg = context.getSharedPreferences("LiftLogPrefs", Context.MODE_PRIVATE)
        .getBoolean("use_kg", false)

    val groupedWorkouts = workouts.groupBy {
        try {
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
            formatter.format(parser.parse(it.date) ?: Date())
        } catch (e: Exception) {
            it.date
        }
    }

    val todayFormatted = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dashboard", fontWeight = FontWeight.Bold) })
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("logWorkout") },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text("Add Exercise", color = Color.White)
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { navController.navigate("preferences") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004D40))
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                        Spacer(Modifier.width(8.dp))
                        Text("Settings")
                    }
                    Button(
                        onClick = { navController.navigate("help") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Icon(Icons.Default.Info, contentDescription = "Help")
                        Spacer(Modifier.width(8.dp))
                        Text("Help")
                    }
                    Button(
                        onClick = { navController.navigate("chart") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) {
                        Icon(Icons.Default.ShowChart, contentDescription = "Progress")
                        Spacer(Modifier.width(8.dp))
                        Text("Progress Tracker")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            groupedWorkouts.forEach { (date, workoutsOnDate) ->
                val isToday = date == todayFormatted
                val displayDate = if (isToday) "Today — $date" else date
                val header = "$displayDate | ${workoutsOnDate.size} Exercise${if (workoutsOnDate.size != 1) "s" else ""}"

                // ✅ Only one PR per exercise (heaviest weight)
                val prIdsForDay = workoutsOnDate
                    .groupBy { it.name }
                    .mapNotNull { (_, entries) ->
                        entries.maxByOrNull { it.weight }
                    }
                    .map { it.id }
                    .toSet()

                item {
                    WorkoutDayCard(
                        date = header,
                        workouts = workoutsOnDate,
                        useKg = useKg,
                        prWorkoutIds = prIdsForDay,
                        onWorkoutClick = { workout ->
                            navController.navigate("edit_workout/${workout.id}")
                        }
                    )
                }
            }
        }
    }
}
