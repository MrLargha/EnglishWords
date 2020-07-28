package ru.mrlargha.englishwords.data.questions

class QuestionWithUserInput(
    override val questionText: String,
    override val rightAnswer: Answer
) : IQuestion {
    override fun checkAnswer(giveAnswer: Answer): Boolean {
        TODO("Not yet implemented")
    }
}