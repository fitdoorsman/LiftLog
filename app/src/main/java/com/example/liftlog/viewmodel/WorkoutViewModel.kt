package com.example.liftlog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.liftlog.data.Workout
import com.example.liftlog.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to manage workout data and interact with repository.
 */
class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {

    // 🔥 Expose allWorkouts as StateFlow for real-time updates
    val allWorkouts: StateFlow<List<Workout>> = repository.allWorkouts
        .map { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.insert(workout)
        }
    }

    fun getWorkoutById(id: Long): Flow<Workout?> {
        return repository.getWorkoutById(id)
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.delete(workout)
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.update(workout)
        }
    }

    fun deleteAllWorkouts() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}

/**
 * Factory for creating WorkoutViewModel instances.
 */
class WorkoutViewModelFactory(private val repository: WorkoutRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
