package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.data.Translation
import ru.mrlargha.englishwords.data.WordWithTranslation

class QuestionFactory(val wordsAssets: List<WordWithTranslation>) {
    fun createQuestionsSet(): List<IQuestion> {
        TODO("Implement")
    }

    private fun createQuestionWithMatching(words: List<WordWithTranslation>): QuestionWithMatching {
        TODO("Implement")
    }

    private fun createQuestionWithSelectableAnswer(
        word: WordWithTranslation,
        answerWords: List<Translation>
    ): QuestionWithSelectableAnswer {
        TODO("Implement")
    }

    private fun createQuestionWithUserInput(word: WordWithTranslation): QuestionWithUserInput {
        TODO("Implement")
    }
}