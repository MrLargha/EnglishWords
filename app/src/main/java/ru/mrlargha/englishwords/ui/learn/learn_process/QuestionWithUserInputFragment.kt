package ru.mrlargha.englishwords.ui.learn.learn_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion
import ru.mrlargha.englishwords.data.questions.QuestionWithUserInput
import ru.mrlargha.englishwords.databinding.FragmentQuestionWithUserInputBinding


class QuestionWithUserInputFragment : QuestionFragment() {

    private lateinit var binding: FragmentQuestionWithUserInputBinding
    private lateinit var question: QuestionWithUserInput

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionWithUserInputBinding.inflate(inflater, container, false)

        binding.translationEditText.addTextChangedListener {
            answerAvailableListener?.invoke(it?.isNotBlank() ?: false)
        }

        return binding.root
    }

    override fun setQuestion(question: IQuestion) {
        this.question = question as QuestionWithUserInput
        binding.word.text = question.word.word.englishWord
    }

    override fun getAnswer(): Answer? = Answer(listOf(binding.translationEditText.text.toString()))

}