package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mrlargha.englishwords.data.LearnSessionResultRepository

@Suppress("UNCHECKED_CAST")
class LearnSessionResultViewModelFactory(
    private val resultRepository: LearnSessionResultRepository,
    private val resultID: Int
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LearnSessionResultViewModel(resultRepository, resultID) as T
    }
}