package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mrlargha.englishwords.data.LearnSessionResultRepository

@Suppress("UNCHECKED_CAST")
class StatisticsViewModelFactory(
    private val resultRepository: LearnSessionResultRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatisticsViewModel(resultRepository) as T
    }
}
