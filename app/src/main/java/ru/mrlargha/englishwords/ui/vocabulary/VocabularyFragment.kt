package ru.mrlargha.englishwords.ui.vocabulary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.mrlargha.englishwords.R
import ru.mrlargha.englishwords.databinding.VocabularyFragmentBinding

class VocabularyFragment : Fragment() {

    private val viewModel: VocabularyViewModel by viewModels()
    private lateinit var binding: VocabularyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = VocabularyFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


}