package com.example.optlearn.mvvm

import android.app.Application
import androidx.lifecycle.*
import com.example.optlearn.room.Task
import com.example.optlearn.room.TaskDatabase.Companion.getInstance
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskViewModel(application: Application) :AndroidViewModel(application) {

        var repository = TaskRepository(application)
    private var list : Deferred<LiveData<List<Task>>> = repository.getAllTaks()

    fun getAllTasks() : LiveData<List<Task>> = runBlocking {
         list.await()
    }

    fun insertTask(task: Task) = repository.insertTask(task)

    fun deleteTask(task: Task) = repository.deleteTask(task)

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {  repository.updateTask(task) }
    }
}