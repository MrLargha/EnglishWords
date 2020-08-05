package ru.mrlargha.englishwords.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mrlargha.englishwords.adapters.CourseAdapter
import ru.mrlargha.englishwords.databinding.FragmentLearnBinding
import ru.mrlargha.englishwords.utility.InjectorUtils
import ru.mrlargha.englishwords.viewmodels.LearnHostViewModel

class LearnFragment : Fragment() {

    private val viewModel: LearnHostViewModel by viewModels {
        InjectorUtils.provideLearnHostViewModelFactory(this)
    }

    private lateinit var binding: FragmentLearnBinding
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnBinding.inflate(layoutInflater, container, false)
        adapter = CourseAdapter()
        binding.coursesRecycler.apply {
            adapter = this@LearnFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.coursesList.observe(viewLifecycleOwner) {
            adapter.courses = it
        }
    }

}