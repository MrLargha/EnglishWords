package ru.mrlargha.englishwords.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mrlargha.englishwords.databinding.FragmentQuestionWithUserInputBinding


class QuestionWithUserInputFragment : Fragment() {

    private lateinit var binding: FragmentQuestionWithUserInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionWithUserInputBinding.inflate(inflater, container, false)

        return binding.root
    }

}