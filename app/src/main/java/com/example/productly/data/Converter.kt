package com.example.productly.data

import androidx.room.TypeConverter
import com.example.productly.data.models.Priority


// Convert to Priority to string
class Converter {
        @TypeConverter
    fun fromPriority (priority: Priority) :String {
        return  priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return  Priority.valueOf(priority)
    }
}