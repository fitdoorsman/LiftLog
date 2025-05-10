package com.example.liftlog.ui.screens

import android.graphics.Color
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.liftlog.viewmodel.WorkoutViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(viewModel: WorkoutViewModel, navController: NavController) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val labelFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
    val today = Calendar.getInstance()

    val labels = mutableListOf<String>()
    val barEntries = mutableListOf<BarEntry>()
    val barColors = mutableListOf<Int>()

    val workouts = runBlocking { viewModel.allWorkouts.first() }

    // 🟡 Count exercises per day
    val groupedCounts = mutableMapOf<String, Int>()
    for (workout in workouts) {
        try {
            val parsedDate = inputFormat.parse(workout.date)
            val normalizedDate = inputFormat.format(parsedDate ?: continue)
            val dateDiff = ((today.timeInMillis - parsedDate.time) / (1000 * 60 * 60 * 24)).toInt()
            if (dateDiff in 0..13) {
                groupedCounts[normalizedDate] = groupedCounts.getOrDefault(normalizedDate, 0) + 1
            }
        } catch (_: Exception) {
            continue
        }
    }

    // ✅ Sort and prepare bar data
    val sortedDates = groupedCounts.keys.sorted()
    val maxCount = groupedCounts.values.maxOrNull() ?: 0

    sortedDates.forEachIndexed { index, date ->
        val count = groupedCounts[date] ?: 0
        barEntries.add(BarEntry(index.toFloat(), count.toFloat()))
        labels.add(labelFormat.format(inputFormat.parse(date) ?: Date()))
        barColors.add(
            if (count == maxCount) Color.rgb(255, 215, 0)
            else Color.rgb(76, 175, 80)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercise Frequency (Last 14 Days)") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            AndroidView(
                factory = { ctx ->
                    BarChart(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

                        val dataSet = BarDataSet(barEntries, "Exercises Per Day")
                        dataSet.colors = barColors
                        dataSet.valueTextSize = 12f
                        val data = BarData(dataSet)
                        data.barWidth = 0.9f

                        this.data = data
                        setFitBars(true)
                        animateY(1000)
                        description.isEnabled = false
                        axisRight.isEnabled = false
                        setDrawGridBackground(false)

                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            valueFormatter = IndexAxisValueFormatter(labels)
                            granularity = 1f
                            isGranularityEnabled = true
                            setDrawGridLines(false)
                            labelRotationAngle = -45f
                        }

                        axisLeft.axisMinimum = 0f
                        invalidate()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.EmojiEvents,
                    contentDescription = "Gold",
                    tint = ComposeColor(0xFFFFD700)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gold bar = Highest activity day", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
