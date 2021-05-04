package com.example.optlearn.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name : String?,
    val time : String?,
    val breaks : Int?,
    val time_breaks : String?,
    //Value 1=success 2=no success 0=null
    var success : Int?
) {

}
