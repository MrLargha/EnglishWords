package ru.mrlargha.englishwords.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey val courseId: Int,
    val courseName: String,
    val courseDescription: String,
    val courseProgress: Int = 0
)