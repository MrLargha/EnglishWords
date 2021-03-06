package ru.mrlargha.englishwords.ui.learn.learn_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.children
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.IQuestion
import ru.mrlargha.englishwords.data.questions.QuestionWithSelectableAnswer
import ru.mrlargha.englishwords.databinding.FragmentQuestionWithSelectableAnswerBinding

class QuestionWithSelectableAnswerFragment : QuestionFragment() {

    private lateinit var binding: FragmentQuestionWithSelectableAnswerBinding
    private lateinit var question: QuestionWithSelectableAnswer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentQuestionWithSelectableAnswerBinding.inflate(inflater, container, false)

        binding.radioGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
            answerAvailableListener?.invoke(true)
        }

        return binding.root
    }

    override fun setQuestion(question: IQuestion) {
        this.question = question as QuestionWithSelectableAnswer

        binding.word.text = question.word.word.englishWord
        question.answerVariants.shuffled().zip(binding.radioGroup.children.toList()).forEach {
            (it.second as MaterialRadioButton).text = it.first
        }
    }

    override fun getAnswer(): Answer? {
        return try {
            Answer(
                listOf(
                    (binding.radioGroup.findViewById(binding.radioGroup.checkedRadioButtonId)
                            as MaterialRadioButton).text
                )
            )
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }
}