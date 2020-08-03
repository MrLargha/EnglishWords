package ru.mrlargha.englishwords.data.questions

import ru.mrlargha.englishwords.data.WordWithTranslation

interface IQuestion {
    fun getWordsWithErrors(givenAnswer: Answer?): List<WordWithTranslation>
}

