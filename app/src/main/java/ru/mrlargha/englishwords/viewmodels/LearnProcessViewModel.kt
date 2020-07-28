package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mrlargha.englishwords.data.WordsRepository
import ru.mrlargha.englishwords.data.questions.IQuestion

class LearnProcessViewModel(
    val wordsRepository: WordsRepository,
    val selectedCourseId: Int
) :
    ViewModel() {
    val currentQuestion = MutableLiveData<IQuestion>()
    val progressPercents = MutableLiveData<Int>()
}