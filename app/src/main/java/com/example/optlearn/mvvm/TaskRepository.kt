package com.example.optlearn.mvvm

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.optlearn.room.Task
import com.example.optlearn.room.TaskDao
import com.example.optlearn.room.TaskDatabase
import kotlinx.coroutines.*

class TaskRepository(application: Application)   {
    lateinit var taskDao :TaskDao
    init {
        val database = TaskDatabase.getInstance(application.applicationContext)
        taskDao = database!!.taskDao()
    }

     fun getAllTaks() : Deferred<LiveData<List<Task>>> =
        CoroutineScope(Dispatchers.IO).async {
        taskDao.getAllTasks()
        }

     fun insertTask(task: Task) =
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.insertTask(task)
        }

    fun deleteTask(task: Task) =
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.deleteTask(task)
        }

}