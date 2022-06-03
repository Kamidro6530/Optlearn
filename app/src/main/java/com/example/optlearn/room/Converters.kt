package com.example.optlearn.room

import androidx.lifecycle.LiveData
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class Converters {

    @TypeConverter
    fun stringToObject(value: String): List<Task> {
        val listType = object : TypeToken<List<Task>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<Task>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
