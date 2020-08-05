package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mrlargha.englishwords.data.CourseRepository

@Suppress("UNCHECKED_CAST")
class LearnHostViewModelFactory(private val courseRepository: CourseRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LearnHostViewModel(courseRepository) as T
    }
}