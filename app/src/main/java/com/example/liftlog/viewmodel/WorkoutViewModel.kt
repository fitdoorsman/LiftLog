package com.example.liftlog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.liftlog.data.Workout
import com.example.liftlog.repository.WorkoutRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel to manage workout data and interact with repository.
 */
class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {

    val allWorkouts = repository.allWorkouts

    fun insertWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.insert(workout)
        }
    }

    fun getWorkoutById(id: Long): Flow<Workout?> {
        return repository.getWorkoutById(id)
    }

    fun addWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.insert(workout)
        }
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

class WorkoutViewModelFactory(private val repository: WorkoutRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}