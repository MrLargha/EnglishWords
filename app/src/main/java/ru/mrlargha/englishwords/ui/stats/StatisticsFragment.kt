package ru.mrlargha.englishwords.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import ru.mrlargha.englishwords.databinding.StatisticsFragmentBinding
import ru.mrlargha.englishwords.utility.InjectorUtils
import ru.mrlargha.englishwords.viewmodels.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsViewModel by viewModels {
        InjectorUtils.provideStatisticsViewModelFactory(this)
    }
    private lateinit var binding: StatisticsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StatisticsFragmentBinding.inflate(layoutInflater)

        binding.apply {
        }

        return binding.root
    }
}