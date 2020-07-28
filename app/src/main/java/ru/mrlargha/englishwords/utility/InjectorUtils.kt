package ru.mrlargha.englishwords.utility

import android.content.Context
import androidx.fragment.app.Fragment
import ru.mrlargha.englishwords.data.AppDatabase
import ru.mrlargha.englishwords.data.WordsRepository
import ru.mrlargha.englishwords.viewmodels.LearnHostViewModelFactory
import ru.mrlargha.englishwords.viewmodels.LearnProcessViewModelFactory

object InjectorUtils {
    private fun getWordsRepository(context: Context): WordsRepository {
        return with(AppDatabase.getInstance(context)) {
            WordsRepository.getInstance(wordDao(), courseDao())
        }
    }

    fun provideLearnProcessViewModelFactory(
        fragment: Fragment,
        courseId: Int
    ): LearnProcessViewModelFactory {
        return LearnProcessViewModelFactory(getWordsRepository(fragment.requireContext()), courseId)
    }

    fun provideLearnHostViewModelFactory(fragment: Fragment): LearnHostViewModelFactory {
        return LearnHostViewModelFactory(getWordsRepository(fragment.requireContext()))
    }
}


