package ru.mrlargha.englishwords.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getCourses(): LiveData<List<Course>>

    @Update
    fun updateCourses(vararg course: Course)

    @Insert
    fun insertCourses(courses: List<Course>)
}