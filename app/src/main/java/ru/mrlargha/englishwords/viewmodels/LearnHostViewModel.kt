package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.mrlargha.englishwords.data.Course
import ru.mrlargha.englishwords.data.CourseRepository

class LearnHostViewModel(courseRepository: CourseRepository) : ViewModel() {
    val coursesList: LiveData<List<Course>> = courseRepository.getCourses()
}