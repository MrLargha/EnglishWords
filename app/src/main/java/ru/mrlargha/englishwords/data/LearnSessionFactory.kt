package ru.mrlargha.englishwords.data

import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion
import ru.mrlargha.englishwords.data.questions.QuestionWithUserInput

class LearnSessionFactory {
    private val questionList = mutableListOf<IQuestion>()

    fun getRequiredWordsWithTranslationsCount(): Int {
        return questionList.sumBy { it.getRequiredWordsWithTranslations() }
    }

    fun getRequiredIndependentTranslations(): Int {
        return questionList.sumBy { it.getRequiredIndependentTranslations() }
    }

    init {
        repeat(2) {
//            questionList.add(QuestionWithMatching())
        }

        repeat(4) {
            questionList.add(QuestionWithUserInput())
//            questionList.add(QuestionWithSelectableAnswer())
        }
    }

    fun create(_words: List<WordWithTranslation>, _translations: List<Translation>): LearnSession {
        val words = _words.toMutableList()
        val translations = _translations.toMutableList()

        for (question in questionList) {
            question.acceptWordsWithTranslations(words.take(question.getRequiredWordsWithTranslations()))
            question.acceptIndependentTranslations(translations.take(question.getRequiredIndependentTranslations()))

            words.drop(question.getRequiredWordsWithTranslations())
            translations.drop(question.getRequiredIndependentTranslations())
        }

        return LearnSession(
            questionList.shuffled().zip(arrayOfNulls<Answer>(questionList.size))
                .toMap().toMutableMap()
        )
    }
}