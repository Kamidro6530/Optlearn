package com.example.optlearn.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity
data class Task(
    val name : String?,
    val time : String?,
    val breaks : Int?,
    val time_breaks : String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
