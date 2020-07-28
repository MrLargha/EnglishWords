package ru.mrlargha.englishwords.ui.learn_process

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.mrlargha.englishwords.R
import ru.mrlargha.englishwords.utility.InjectorUtils
import ru.mrlargha.englishwords.viewmodels.LearnProcessViewModel


class LearnProcessFragment(private val courseId: Int) : Fragment() {

    private val viewModel: LearnProcessViewModel by viewModels {
        InjectorUtils.provideLearnProcessViewModelFactory(this, courseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn_process, container, false)
    }
}