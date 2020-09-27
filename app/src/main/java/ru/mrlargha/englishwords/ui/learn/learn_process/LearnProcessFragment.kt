package ru.mrlargha.englishwords.ui.learn.learn_process

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.mrlargha.englishwords.data.questions.QuestionWithSelectableAnswer
import ru.mrlargha.englishwords.data.questions.QuestionWithUserInput
import ru.mrlargha.englishwords.databinding.FragmentLearnProcessBinding
import ru.mrlargha.englishwords.utility.InjectorUtils
import ru.mrlargha.englishwords.viewmodels.LearnProcessViewModel


class LearnProcessFragment : Fragment() {

    private val args: LearnProcessFragmentArgs by navArgs()
    private val viewModel: LearnProcessViewModel by viewModels {
        InjectorUtils.provideLearnProcessViewModelFactory(this, args.courseID)
    }

    private lateinit var currentQuestionFragment: QuestionFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLearnProcessBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(this, true) {
            MaterialAlertDialogBuilder(requireContext()).setTitle("Exit learning?")
                .setMessage("If you quit the current progress will be lost!")
                .setPositiveButton("Quit") { _: DialogInterface, _: Int ->
                    findNavController().navigateUp()
                }.setNegativeButton("Continue") { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }.show()
        }

        return binding.apply {
            progressText.text = "Loading questions..."
            courseName.text = "Course: ${args.courseName}"
            subscribeUI(this)
        }.root
    }

    private fun subscribeUI(binding: FragmentLearnProcessBinding) {
        viewModel.currentQuestionNumber.observe(viewLifecycleOwner) {
            updateProgress(it, binding)
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner) { newQuestion ->
            currentQuestionFragment = when (newQuestion) {
                is QuestionWithSelectableAnswer -> QuestionWithSelectableAnswerFragment()
                is QuestionWithUserInput -> QuestionWithUserInputFragment()
                else -> throw IllegalArgumentException()
            }

            binding.next.isEnabled = false

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(binding.questionFragmentContainer.id, currentQuestionFragment)
                .runOnCommit {
                    currentQuestionFragment.setQuestion(newQuestion)
                    binding.skip.isEnabled = true
                    currentQuestionFragment.answerAvailableListener = {
                        binding.next.isEnabled = it
                    }
                }.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
        }

        viewModel.sessionResultId.observe(viewLifecycleOwner) {
            val action = LearnProcessFragmentDirections
                .actionLearnProcessFragmentToLearnSessionResultFragment(it)
            findNavController().navigate(action)
        }

        binding.next.setOnClickListener {
            viewModel.postAnswer(currentQuestionFragment.getAnswer())
            viewModel.nextQuestion()
        }

        binding.skip.setOnClickListener {
            viewModel.nextQuestion()
        }
    }

    private fun updateProgress(progress: Int, binding: FragmentLearnProcessBinding) {
        if (viewModel.questionsCount == 0)
            return
        binding.progressText.text = "Question $progress of ${viewModel.questionsCount}"
        binding.progressBar.isIndeterminate = false
        binding.progressBar.progress = (100 / viewModel.questionsCount) * progress
    }

    override fun onStop() {
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""
        super.onStop()
    }
}