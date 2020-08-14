package ru.mrlargha.englishwords.data

import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion

class LearnSession(val questionsAndAnswers: MutableMap<IQuestion, Answer?>) {
    fun getSuccessWords(): List<Word> =
        questionsAndAnswers.flatMap {
            it.key.getAllWords().minus(it.key.getWordsWithErrors(it.value).map { wwt -> wwt.word })
        }

    fun getWordsWithErrors(): List<Word> =
        questionsAndAnswers.flatMap { it.key.getWordsWithErrors(it.value) }.map { it.word }
}