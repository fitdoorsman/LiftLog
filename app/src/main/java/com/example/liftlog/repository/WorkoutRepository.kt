package com.example.liftlog.repository

import com.example.liftlog.data.Workout
import com.example.liftlog.data.WorkoutDao
import kotlinx.coroutines.flow.Flow

/**
 * Repository layer for workout data operations.
 */
class WorkoutRepository(private val workoutDao: WorkoutDao) {

    // All workout entries ordered by most recent date
    val allWorkouts: Flow<List<Workout>> = workoutDao.getAllWorkouts()

    // Personal records: highest weight per exercise
    val personalRecords: Flow<List<Workout>> = workoutDao.getPersonalRecords()

    suspend fun insert(workout: Workout) = workoutDao.insert(workout)

    suspend fun delete(workout: Workout) = workoutDao.delete(workout)

    suspend fun update(workout: Workout) = workoutDao.update(workout)

    suspend fun deleteAll() = workoutDao.deleteAll()

    fun getWorkoutById(id: Long): Flow<Workout?> {
        return workoutDao.getWorkoutById(id)
    }
}
