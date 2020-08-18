package ru.mrlargha.englishwords.utility

import android.content.Context
import androidx.fragment.app.Fragment
import ru.mrlargha.englishwords.data.AppDatabase
import ru.mrlargha.englishwords.data.CourseRepository
import ru.mrlargha.englishwords.data.LearnSessionResultRepository
import ru.mrlargha.englishwords.data.WordsRepository
import ru.mrlargha.englishwords.viewmodels.LearnHostViewModelFactory
import ru.mrlargha.englishwords.viewmodels.LearnProcessViewModelFactory
import ru.mrlargha.englishwords.viewmodels.LearnSessionResultViewModelFactory
import ru.mrlargha.englishwords.viewmodels.StatisticsViewModelFactory

object InjectorUtils {
    private fun getWordsRepository(context: Context) =
        with(AppDatabase.getInstance(context)) {
            WordsRepository.getInstance(wordDao())
        }


    private fun getCourseRepository(context: Context) =
        with(AppDatabase.getInstance(context)) {
            CourseRepository.getInstance(courseDao())
        }


    private fun getLearnSessionResultRepository(context: Context) =
        with(AppDatabase.getInstance(context)) {
            LearnSessionResultRepository.getInstance(learnSessionResultDao())
        }


    fun provideLearnProcessViewModelFactory(
        fragment: Fragment,
        courseId: Int
    ) =
        LearnProcessViewModelFactory(
            getWordsRepository(fragment.requireContext()),
            getLearnSessionResultRepository(fragment.requireContext()),
            courseId
        )

    fun provideLearnHostViewModelFactory(fragment: Fragment) =
        LearnHostViewModelFactory(getCourseRepository(fragment.requireContext()))


    fun provideLearnSessionResultViewModelFactory(fragment: Fragment, resultID: Int) =
        LearnSessionResultViewModelFactory(
            getLearnSessionResultRepository(fragment.requireContext()),
            resultID
        )

    fun provideStatisticsViewModelFactory(fragment: Fragment) =
        StatisticsViewModelFactory(getLearnSessionResultRepository(fragment.requireContext()))

}


