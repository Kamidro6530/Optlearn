package com.example.optlearn.room

import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.Deferred
import java.util.*

@Entity
@TypeConverters(Converters::class)
data class Group(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var list: List<Task>,
    var name: String
)
