package com.example.liftlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a workout entry in the database.
 */
@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,     // Exercise name
    val date: String,     // Format: YYYY-MM-DD
    val weight: Float,    // Weight lifted
    val sets: Int,        // Number of sets
    val reps: Int,        // Number of reps per set
    val notes: String?    // Optional notes
)