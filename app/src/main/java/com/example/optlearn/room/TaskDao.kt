package com.example.optlearn.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.Deferred

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
     fun getAllTasks() : LiveData<List<Task>>

    @Insert
      fun insertTask ( vararg  task : Task)

    @Delete
     fun deleteTask (vararg task : Task)

     @Update(onConflict = OnConflictStrategy.REPLACE)
      suspend fun updateTask(vararg  task : Task)
}