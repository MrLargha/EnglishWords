package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.BuildConfig
import ru.mrlargha.englishwords.data.WordWithTranslation

class QuestionWithMatching(
    private val words: List<WordWithTranslation>
) :
    IQuestion {

    companion object {
        const val REQUIRED_WORDS = 4
        const val REQUIRED_TRANSLATIONS = 0
    }

    private val rightAnswer = Answer(words.map { it.translations.random() })

    val shuffledAnswers = rightAnswer.answersData.shuffled()

    init {
        if (BuildConfig.DEBUG && shuffledAnswers.size != REQUIRED_WORDS) {
            error("Assertion failed")
        }
    }

    override fun getWordsWithErrors(givenAnswer: Answer?): List<WordWithTranslation> {
        givenAnswer ?: return words
        val result = mutableListOf<WordWithTranslation>()
        for (i in 0..words.lastIndex) {
            if (rightAnswer.answersData[i] != givenAnswer.answersData[i]) {
                result.add(words[i])
            }
        }
        return result
    }
}