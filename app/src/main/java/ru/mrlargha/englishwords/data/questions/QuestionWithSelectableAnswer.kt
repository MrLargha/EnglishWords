package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.BuildConfig
import ru.mrlargha.englishwords.data.Translation
import ru.mrlargha.englishwords.data.WordWithTranslation

class QuestionWithSelectableAnswer(
    val word: WordWithTranslation,
    private val wrongAnswers: List<Translation>
) : IQuestion {

    companion object {
        const val REQUIRED_WORDS = 4
        const val REQUIRED_TRANSLATIONS = 0
    }

    private val rightAnswer =
        Answer(listOf(word.translations.filter { it !in wrongAnswers }.random().translation))

    val answerVariants: List<String> = listOf(
        *(wrongAnswers.map { it.translation }.toTypedArray()),
        rightAnswer.answersData.first() as String
    )

    init {
        if (BuildConfig.DEBUG && answerVariants.size == REQUIRED_WORDS) {
            error("Assertion failed")
        }
    }

    override fun getWordsWithErrors(givenAnswer: Answer?): List<WordWithTranslation> {
        givenAnswer ?: return emptyList()
        return if (givenAnswer.answersData.none { it in rightAnswer.answersData }) listOf(word) else emptyList()
    }

}