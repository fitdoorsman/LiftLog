package com.example.liftlog.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE id = :id LIMIT 1")
    fun getWorkoutById(id: Long): Flow<Workout?>

    @Query("""
        SELECT * FROM workouts 
        WHERE (name, weight) IN (
            SELECT name, MAX(weight) 
            FROM workouts 
            GROUP BY name
        )
        ORDER BY name
    """)
    fun getPersonalRecords(): Flow<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Query("DELETE FROM workouts")
    suspend fun deleteAll()
}