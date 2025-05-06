package com.example.liftlog

import android.app.Application
import com.example.liftlog.data.WorkoutDatabase
import com.example.liftlog.repository.WorkoutRepository

class LiftLogApplication : Application() {
    val repository: WorkoutRepository by lazy {
        WorkoutRepository(WorkoutDatabase.getDatabase(this).workoutDao())
    }
}