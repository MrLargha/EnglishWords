package ru.mrlargha.englishwords.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mrlargha.englishwords.data.LearnSession
import ru.mrlargha.englishwords.data.LearnSessionFactory
import ru.mrlargha.englishwords.data.WordsRepository
import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion

class LearnProcessViewModel(
    private val wordsRepository: WordsRepository,
    private val selectedCourseId: Int
) :
    ViewModel() {

    val currentQuestion = MutableLiveData<IQuestion>()
    val currentQuestionNumber = MutableLiveData<Int>()
    val sessionResultId = MutableLiveData<Int>()
    var questionsCount = 0

    private var currentQuestionId = 0
    private lateinit var learnSession: LearnSession

    init {
        viewModelScope.launch {
            createQuestions()
            currentQuestionId = -1
            nextQuestion()
        }
//        learnState.postValue(LearnState.IN_PROGRESS)
    }

    fun nextQuestion() {
        currentQuestionId++

        if (currentQuestionId >= questionsCount) {
            TODO("Impl")
        } else {
            currentQuestion.postValue(learnSession.questionsAndAnswers.keys.toList()[currentQuestionId])
            currentQuestionNumber.postValue(currentQuestionId + 1)
        }
    }

    fun postAnswer(answer: Answer?) {
        learnSession.questionsAndAnswers[checkNotNull(currentQuestion.value)] = answer
    }

    private suspend fun createQuestions() {
        val learnSessionFactory = LearnSessionFactory()
        val amountOfWords = learnSessionFactory.getRequiredWordsWithTranslationsCount()
        val words = wordsRepository.getWordsWithUserErrors(
            amountOfWords,
            selectedCourseId
        ).toMutableList()

        if (words.size < amountOfWords) {
            words.addAll(
                wordsRepository.getNewWords(
                    amountOfWords - words.size,
                    selectedCourseId
                )
            )
        }

        val translations =
            wordsRepository.getTranslations(learnSessionFactory.getRequiredIndependentTranslations())

        learnSession = learnSessionFactory.create(words, translations)

        questionsCount = learnSession.questionsAndAnswers.keys.size
    }
}