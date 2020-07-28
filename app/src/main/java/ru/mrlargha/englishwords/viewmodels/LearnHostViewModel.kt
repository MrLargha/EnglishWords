package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.ViewModel
import ru.mrlargha.englishwords.data.WordsRepository

class LearnHostViewModel(private val wordsRepository: WordsRepository) : ViewModel()