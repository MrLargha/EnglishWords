package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.data.WordWithTranslation

class QuestionWithMatching : IQuestion {

    companion object {
        const val REQUIRED_WORDS = 4
    }

    lateinit var words: List<WordWithTranslation>
    lateinit var rightAnswer: Answer
    lateinit var shuffledAnswers: List<String>

    override fun getRequiredWordsWithTranslations(): Int = REQUIRED_WORDS

    override fun acceptWordsWithTranslations(words: List<WordWithTranslation>) {
        this.words = words
        val answersData = words.map { it.translations.random().translationText }
        rightAnswer = Answer(answersData)
        shuffledAnswers = answersData.shuffled()
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