package com.example.optlearn.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListOfTasks (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val list : List<Task>
)