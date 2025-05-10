package com.example.liftlog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a workout entry in the database.
 */
@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val date: String,
    val weight: Float,
    val sets: Int,
    val reps: Int,
    val notes: String?
)
