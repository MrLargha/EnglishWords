package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mrlargha.englishwords.data.WordsRepository

@Suppress("UNCHECKED_CAST")
class LearnHostViewModelFactory(private val wordsRepository: WordsRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LearnHostViewModel(wordsRepository) as T
    }
}