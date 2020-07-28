package ru.mrlargha.englishwords.data.questions

interface IQuestion {
    val rightAnswer: Answer
    val questionText: String

    fun checkAnswer(giveAnswer: Answer): Boolean = rightAnswer == giveAnswer
}

