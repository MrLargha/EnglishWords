package ru.mrlargha.englishwords.utility

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun timestampToDate(timestamp: Long): Date = Date(timestamp)
}