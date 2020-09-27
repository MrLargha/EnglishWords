package ru.mrlargha.englishwords.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mrlargha.englishwords.adapters.WordAdapter
import ru.mrlargha.englishwords.databinding.LearnSessionResultFragmentBinding
import ru.mrlargha.englishwords.utility.InjectorUtils
import ru.mrlargha.englishwords.viewmodels.LearnSessionResultViewModel

class LearnSessionResultFragment : Fragment() {

    private val args: LearnSessionResultFragmentArgs by navArgs()
    private val viewModel by viewModels<LearnSessionResultViewModel> {
        InjectorUtils.provideLearnSessionResultViewModelFactory(this, args.resultID)
    }

    private lateinit var binding: LearnSessionResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LearnSessionResultFragmentBinding.inflate(inflater, container, false)

        val rightAdapter = WordAdapter()
        val wrongAdapter = WordAdapter()

        binding.apply {
            rightWordsRecyclerView.adapter = rightAdapter
            wrongWordsRecyclerView.adapter = wrongAdapter
            rightWordsRecyclerView.isNestedScrollingEnabled = false
            wrongWordsRecyclerView.isNestedScrollingEnabled = false
            rightWordsRecyclerView.layoutManager =
                LinearLayoutManager(this@LearnSessionResultFragment.context)
            wrongWordsRecyclerView.layoutManager =
                LinearLayoutManager(this@LearnSessionResultFragment.context)
        }

        subscribeUI(rightAdapter, wrongAdapter, binding)

        return binding.root
    }

    private fun subscribeUI(
        rightAdapter: WordAdapter, wrongAdapter: WordAdapter,
        binding: LearnSessionResultFragmentBinding
    ) {
        viewModel.learnSessionResult.observe(viewLifecycleOwner) {
            binding.apply {
                title.text = "Learn session #${it.lsr.id}"
                wordsGuessed.text = it.lsr.successPercents.toString() + '%'
                newWords.text = it.lsr.newWordsPercent.toString() + '%'

                rightAdapter.submitList(it.details.filter { it.lsrDetails.success }.map { it.word })
                wrongAdapter.submitList(it.details.filter { !it.lsrDetails.success }
                    .map { it.word })

                if (rightAdapter.itemCount == 0) {
                    rightWordsRecyclerView.visibility = View.GONE
                    rightwordsText.visibility = View.GONE
                }

                if (wrongAdapter.itemCount == 0) {
                    wrongWordsRecyclerView.visibility = View.GONE
                    wrongWordsText.visibility = View.GONE
                }

            }
        }
    }

}