package ru.mrlargha.englishwords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getCourses(): LiveData<List<Course>>

    @Query("SELECT * FROM courses")
    fun getCoursesSync(): List<Course>
}