package com.example.optlearn.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.Deferred

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAllTasks() : LiveData<List<Task>>

    @Insert
     fun insertTask ( vararg  task : Task)

    @Delete
     fun deleteTask (vararg task : Task)
}