package com.example.liftlog.viewmodel

data class Workout(
    val exercise: String,
    val weight: Int,
    val sets: Int,
    val reps: Int,
    val notes: String = "",
    val date: String = getCurrentDate()
)

// Optional helper function to generate today's date as a string
fun getCurrentDate(): String {
    val formatter = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
    return formatter.format(java.util.Date())
}