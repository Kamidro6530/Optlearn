package com.example.optlearn.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        var instance: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context, TaskDatabase::class.java, "task-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return instance
        }
    }
}