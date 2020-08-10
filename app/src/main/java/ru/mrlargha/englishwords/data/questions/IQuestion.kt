package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.data.Translation
import ru.mrlargha.englishwords.data.WordWithTranslation

interface IQuestion {
    fun getWordsWithErrors(givenAnswer: Answer?): List<WordWithTranslation>
    fun isCorrect(givenAnswer: Answer?): Boolean = getWordsWithErrors(givenAnswer).isEmpty()
    fun getHTMLQuestionText(): String

    fun getRequiredWordsWithTranslations(): Int = 0
    fun getRequiredIndependentTranslations(): Int = 0

    fun acceptWordsWithTranslations(words: List<WordWithTranslation>) {}
    fun acceptIndependentTranslations(translations: List<Translation>) {}
}

