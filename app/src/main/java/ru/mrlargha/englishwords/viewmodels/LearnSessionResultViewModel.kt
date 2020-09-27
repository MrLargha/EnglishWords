package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrlargha.englishwords.data.LearnSessionResultRepository
import ru.mrlargha.englishwords.data.LearnSessionResultWithDetails

class LearnSessionResultViewModel(
    private val learnSessionResultRepository: LearnSessionResultRepository,
    private val resultID: Int
) :
    ViewModel() {
    val learnSessionResult = MutableLiveData<LearnSessionResultWithDetails>()

    init {
        viewModelScope.launch {
            learnSessionResult.postValue(learnSessionResultRepository.getResultWithDetails(resultID))
        }
    }
}