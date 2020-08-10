package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.data.WordWithTranslation

class QuestionWithUserInput : IQuestion {

    companion object {
        const val REQUIRED_WORDS = 1
    }

    override fun getRequiredWordsWithTranslations() = REQUIRED_WORDS

    private lateinit var rightAnswer: Answer
    lateinit var word: WordWithTranslation

    override fun acceptWordsWithTranslations(words: List<WordWithTranslation>) {
        word = words.first()
        rightAnswer = Answer(word.translations.map { it.translationText })
    }

    override fun getWordsWithErrors(givenAnswer: Answer?): List<WordWithTranslation> {
        givenAnswer ?: return emptyList()
        return if (givenAnswer.answersData.none { it in rightAnswer.answersData }) listOf(word)
        else emptyList()
    }

    override fun getHTMLQuestionText(): String =
        "Enter the translation of word <b>${word.word.englishWord}</b>"
}