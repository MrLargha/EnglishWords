package ru.mrlargha.englishwords.ui.learn_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.mrlargha.englishwords.databinding.FragmentLearnProcessBinding
import ru.mrlargha.englishwords.utility.InjectorUtils
import ru.mrlargha.englishwords.viewmodels.LearnProcessViewModel


class LearnProcessFragment : Fragment() {

    private val viewModel: LearnProcessViewModel by viewModels {
        InjectorUtils.provideLearnProcessViewModelFactory(
            this,
            requireArguments().getInt("courseID")
        )
    }

    private lateinit var binding: FragmentLearnProcessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnProcessBinding.inflate(inflater, container, false)

        binding.courseName.text = requireArguments().getInt("courseID").toString()

        return binding.root
    }


}