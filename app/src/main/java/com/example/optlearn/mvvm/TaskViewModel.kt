package com.example.optlearn.mvvm

import android.app.Application
import androidx.lifecycle.*
import com.example.optlearn.room.Group
import com.example.optlearn.room.Task
import com.example.optlearn.room.TaskDatabase.Companion.getInstance
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskViewModel(application: Application) :AndroidViewModel(application) {

        var repository = TaskRepository(application)
    private var listOfTasks : Deferred<LiveData<List<Task>>> = repository.getAllTaks()
    private var listofGroups : Deferred<LiveData<List<Group>>> = repository.getAllGroups()

    fun getAllTasks() : LiveData<List<Task>> = runBlocking {
        listOfTasks.await()
    }

    fun insertTask(task: Task) = repository.insertTask(task)

    fun deleteTask(task: Task) = repository.deleteTask(task)

    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {  repository.updateTask(task) }
    }
    fun getAllGroups() : LiveData<List<Group>> = runBlocking {
        listofGroups.await()
    }

    fun insertGroup(group: Group) = repository.insertTask(group)

    fun deleteGroup(group: Group) = repository.deleteTask(group)

    fun updateGroup(group: Group){
        viewModelScope.launch(Dispatchers.IO) {  repository.updateTask(group) }
    }
}