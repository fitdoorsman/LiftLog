package com.example.liftlog.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.example.liftlog.data.Workout

@Composable
fun ChartPreview(workouts: List<Workout>) {
    val entries = workouts.mapIndexed { index, workout ->
        Entry(index.toFloat(), workout.weight)
    }

    if (entries.isNotEmpty()) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp),
            factory = { context ->
                LineChart(context).apply {
                    data = LineData(LineDataSet(entries, "Weight").apply {
                        valueTextSize = 10f
                        setDrawCircles(true)
                        setDrawValues(false)
                    })
                    description.isEnabled = false
                    legend.isEnabled = false
                    axisRight.isEnabled = false
                    xAxis.isEnabled = false
                }
            }
        )
    }
}
