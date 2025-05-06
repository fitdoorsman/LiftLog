package com.example.liftlog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.liftlog.data.WorkoutDatabase
import com.example.liftlog.repository.WorkoutRepository
import com.example.liftlog.ui.screens.ChartScreen
import com.example.liftlog.ui.theme.LiftLogTheme
import com.example.liftlog.viewmodel.WorkoutViewModel
import com.example.liftlog.viewmodel.WorkoutViewModelFactory

class ChartActivity : ComponentActivity() {

    // ViewModel with Factory to pass repository
    private val viewModel: WorkoutViewModel by viewModels {
        WorkoutViewModelFactory(
            WorkoutRepository(
                WorkoutDatabase.getDatabase(applicationContext).workoutDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiftLogTheme {
                ChartScreen(viewModel = viewModel)
            }
        }
    }
}