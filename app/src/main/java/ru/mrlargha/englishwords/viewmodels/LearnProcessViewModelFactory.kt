package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mrlargha.englishwords.data.LearnSessionResultRepository
import ru.mrlargha.englishwords.data.WordsRepository

@Suppress("UNCHECKED_CAST")
class LearnProcessViewModelFactory(
    private val wordsRepository: WordsRepository,
    private val learnSessionResultRepository: LearnSessionResultRepository,
    private val selectedCourseId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LearnProcessViewModel(
            wordsRepository,
            learnSessionResultRepository,
            selectedCourseId
        ) as T
    }
}