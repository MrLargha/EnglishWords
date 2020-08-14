package ru.mrlargha.englishwords.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.mrlargha.englishwords.data.*
import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion
import java.util.*
import kotlin.system.measureTimeMillis

class LearnProcessViewModel(
    private val wordsRepository: WordsRepository,
    private val learnSessionResultRepository: LearnSessionResultRepository,
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
            val timeNano = measureTimeMillis {
                createQuestions()
                currentQuestionId = -1
                nextQuestion()
            }
            Log.d("TAG", "time: $timeNano ms")
        }
    }

    fun nextQuestion() {
        currentQuestionId++

        if (currentQuestionId >= questionsCount) {
            finishSession()
        } else {
            currentQuestion.postValue(learnSession.questionsAndAnswers.keys.toList()[currentQuestionId])
            currentQuestionNumber.postValue(currentQuestionId + 1)
        }
    }

    private fun finishSession() {
        viewModelScope.launch {
            var right: Int
            var wrong: Int
            wordsRepository.updateWords(
                learnSession.getSuccessWords().also { right = it.size }.map {
                    it.apply {
                        rightAnswersInRow += 1
                        wasShownToUser = true
                    }
                }.plus(learnSession.getWordsWithErrors().also { wrong = it.size }.map {
                    it.apply {
                        wasShownToUser = true
                        rightAnswersInRow = 0
                    }
                })
            )

            val successPercents = 100 * right / (right + wrong)
            val result = LearnSessionResult(
                learnDate = Date(), relatedCourseID = selectedCourseId,
                successPercents = successPercents
            )

            sessionResultId.postValue(learnSessionResultRepository.insertResult(result))
        }
    }

    fun postAnswer(answer: Answer?) {
        learnSession.questionsAndAnswers[checkNotNull(currentQuestion.value)] = answer
    }

    private suspend fun createQuestions() {
        val learnSessionFactory = LearnSessionFactory()
        val amountOfWords = learnSessionFactory.getRequiredWordsWithTranslationsCount()
        val amountOfTranslations = learnSessionFactory.getRequiredIndependentTranslations()
        val wordsFuture = viewModelScope.async {
            wordsRepository.getWordsWithUserErrors(
                amountOfWords,
                selectedCourseId
            ).toMutableList()
        }

        val translations =
            viewModelScope.async { wordsRepository.getTranslations(amountOfTranslations) }

        val words = wordsFuture.await()

        if (words.size < amountOfWords) {
            words.addAll(
                wordsRepository.getNewWords(
                    amountOfWords - words.size,
                    selectedCourseId
                )
            )
        }

        learnSession = learnSessionFactory.create(words, translations.await())
        questionsCount = learnSession.questionsAndAnswers.keys.size
    }
}