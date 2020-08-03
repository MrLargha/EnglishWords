package ru.mrlargha.englishwords.data

import ru.mrlargha.englishwords.data.questions.*

class LearnSessionFactory {
    companion object {

        const val WORDS_FOR_SESSION = 12
        const val TRANSLATIONS_FOR_SESSION = 12
        const val QUESTIONS_COUNT = 9

        fun createDefaultSession(
            words: List<WordWithTranslation>,
            translations: List<Translation>
        ): LearnSession {
            var wordsOperating = words
            var translationsOperating = translations
            if (wordsOperating.size < WORDS_FOR_SESSION
                || translationsOperating.size < TRANSLATIONS_FOR_SESSION
            )
                throw IllegalArgumentException(
                    "Not enough words! " +
                            "Given ${words.size} should be $WORDS_FOR_SESSION"
                )

            val questions = mutableListOf<IQuestion>()
            questions.add(
                QuestionWithMatching(
                    wordsOperating.subList(
                        0,
                        QuestionWithMatching.REQUIRED_WORDS
                    )
                )
            )
            wordsOperating = wordsOperating.drop(QuestionWithMatching.REQUIRED_WORDS)

            repeat(4) {
                questions.add(QuestionWithUserInput(wordsOperating.first()))
                wordsOperating.drop(1)
            }

            repeat(4) {
                questions.add(
                    QuestionWithSelectableAnswer(
                        wordsOperating.first(),
                        translationsOperating.subList(
                            0,
                            QuestionWithSelectableAnswer.REQUIRED_TRANSLATIONS
                        )
                    )
                )
                wordsOperating.drop(1)
                translationsOperating =
                    translationsOperating.drop(QuestionWithSelectableAnswer.REQUIRED_TRANSLATIONS)
            }

            return LearnSession(
                questions.shuffled().zip(arrayOfNulls<Answer>(QUESTIONS_COUNT)).toMap()
            )
        }
    }
}