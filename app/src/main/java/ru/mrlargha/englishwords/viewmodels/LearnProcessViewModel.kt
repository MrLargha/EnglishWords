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
            val rightWords = learnSession.getSuccessWords()
            val wrongWords = learnSession.getWordsWithErrors()

            wordsRepository.updateWords(
                rightWords.map {
                    it.apply {
                        rightAnswersInRow += 1
                        wasShownToUser = true
                    }
                }.plus(wrongWords.map {
                    it.apply {
                        wasShownToUser = true
                        rightAnswersInRow = 0
                    }
                })
            )

            val successPercents = 100 * rightWords.size / (rightWords.size + wrongWords.size)

            val resultID = learnSessionResultRepository.insertResult(
                LearnSessionResult(Date(), selectedCourseId, successPercents, 0)
            )

            val learnSessionResultDetails = mutableListOf<LearnSessionResultDetail>()

            rightWords.forEach {
                learnSessionResultDetails.add(
                    LearnSessionResultDetail(
                        resultID, it.wordId, true
                    )
                )
            }
            rightWords.apply {
                Log.d("TAG", "${rightWords.size}")
            }
            wrongWords.forEach {
                learnSessionResultDetails.add(
                    LearnSessionResultDetail(
                        resultID, it.wordId, false
                    )
                )
            }

            learnSessionResultRepository.insertResultDetails(learnSessionResultDetails)

            sessionResultId.postValue(resultID)
        }
    }

    fun postAnswer(answer: Answer?) {
        learnSession.questionsAndAnswers[checkNotNull(currentQuestion.value)] = answer
    }
}