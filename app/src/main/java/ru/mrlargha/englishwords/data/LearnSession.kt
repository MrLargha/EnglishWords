package ru.mrlargha.englishwords.data

import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion

typealias QuestionAndAnswer = Pair<IQuestion, Answer?>

class LearnSession(val questionsAndAnswers: Map<IQuestion, Answer?>) {
    fun getWrongAnswers(): List<QuestionAndAnswer> {
        return questionsAndAnswers.filter { it.key.getWordsWithErrors(it.value).isNotEmpty() }
            .toList()
    }
}