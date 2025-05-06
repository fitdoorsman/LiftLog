package com.example.liftlog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.liftlog.viewmodel.WorkoutViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.compose.runtime.collectAsState

@Composable
fun ChartPreview(viewModel: WorkoutViewModel) {
    val workouts by viewModel.allWorkouts.collectAsState(initial = emptyList())

    val lineDataEntries = workouts.mapIndexed { index, workout ->
        Entry(index.toFloat(), workout.weight)
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                setTouchEnabled(false)
                setScaleEnabled(false)

                val dataSet = LineDataSet(lineDataEntries, "Recent Weights").apply {
                    setDrawCircles(true)
                    setDrawValues(false)
                }

                data = LineData(dataSet)
                invalidate()
            }
        }
    )
}