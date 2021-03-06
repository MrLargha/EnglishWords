package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.data.Translation
import ru.mrlargha.englishwords.data.Word
import ru.mrlargha.englishwords.data.WordWithTranslation

class QuestionWithSelectableAnswer : IQuestion {

    companion object {
        const val REQUIRED_WORDS_WITH_TRANSLATION = 1
        const val REQUIRED_TRANSLATIONS = 3
    }

    private lateinit var rightAnswer: Answer
    lateinit var word: WordWithTranslation
    val answerVariants = mutableListOf<String>()

    override fun getRequiredWordsWithTranslations() = REQUIRED_WORDS_WITH_TRANSLATION

    override fun getRequiredIndependentTranslations() = REQUIRED_TRANSLATIONS

    override fun acceptWordsWithTranslations(words: List<WordWithTranslation>) {
        word = words.first()
        val rightTranslation = words.first().translations.random()
        answerVariants.add(rightTranslation.translationText)
        rightAnswer = Answer(listOf(rightTranslation.translationText))
    }

    override fun acceptIndependentTranslations(translations: List<Translation>) {
        answerVariants.addAll(translations.map { it.translationText })
    }

    override fun getWordsWithErrors(givenAnswer: Answer?): List<WordWithTranslation> {
        givenAnswer ?: return listOf(word)
        return if (givenAnswer.answersData.none { it in rightAnswer.answersData })
            listOf(word) else emptyList()
    }

    override fun getAllWords(): List<Word> = listOf(word.word)

    override fun getHTMLQuestionText(): String =
        "Select exactly ONE right translation of word <b>${word.word.englishWord}</b>"

}