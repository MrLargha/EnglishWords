package ru.mrlargha.englishwords.ui.learn.learn_process

import androidx.fragment.app.Fragment
import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion

abstract class QuestionFragment : Fragment() {
    var answerAvailableListener: ((available: Boolean) -> Unit)? = null

    abstract fun setQuestion(question: IQuestion)
    abstract fun getAnswer(): Answer?
}