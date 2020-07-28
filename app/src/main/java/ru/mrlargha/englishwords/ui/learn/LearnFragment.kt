package ru.mrlargha.englishwords.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.mrlargha.englishwords.databinding.FragmentLearnBinding
import ru.mrlargha.englishwords.utility.InjectorUtils
import ru.mrlargha.englishwords.viewmodels.LearnHostViewModel
import ru.mrlargha.englishwords.viewmodels.LearnProcessViewModel

class LearnFragment : Fragment() {

    private val viewModel: LearnHostViewModel by viewModels {
        InjectorUtils.provideLearnHostViewModelFactory(this)
    }
    private lateinit var binding: FragmentLearnBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLearnBinding.inflate(layoutInflater, container, false)

        //TODO: Bind UI and subscribe view model

        return binding.root
    }
}