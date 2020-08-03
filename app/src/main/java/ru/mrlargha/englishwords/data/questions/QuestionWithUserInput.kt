package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.data.WordWithTranslation

class QuestionWithUserInput(
    val word: WordWithTranslation
) : IQuestion {

    companion object {
        const val REQUIRED_WORDS = 1
        const val REQUIRED_TRANSLATIONS = 0
    }

    private val rightAnswer = Answer(listOf(word.translations))

    override fun getWordsWithErrors(givenAnswer: Answer?): List<WordWithTranslation> =
        if (givenAnswer == rightAnswer) emptyList() else listOf(word)
}