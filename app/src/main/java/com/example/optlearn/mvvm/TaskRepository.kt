package com.example.optlearn.mvvm

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.optlearn.room.Group
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
    suspend fun updateTask(task: Task)
         {
            taskDao.updateTask(task)
        }


    fun getAllGroups() : Deferred<LiveData<List<Group>>> =
        CoroutineScope(Dispatchers.IO).async {
            taskDao.getAllGroups()
        }

    fun insertTask(group: Group) =
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.insertGroup(group)
        }

    fun deleteTask(group: Group) =
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.deleteGroup(group)
        }
    suspend fun updateTask(group: Group)
    {
        CoroutineScope(Dispatchers.IO).launch {
            taskDao.updateGroup(group)
        }
    }

}