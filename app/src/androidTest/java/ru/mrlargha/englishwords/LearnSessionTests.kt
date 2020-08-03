package ru.mrlargha.englishwords

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.mrlargha.englishwords.data.*
import ru.mrlargha.englishwords.data.questions.Answer
import ru.mrlargha.englishwords.data.questions.QuestionWithMatching
import ru.mrlargha.englishwords.data.questions.QuestionWithSelectableAnswer
import ru.mrlargha.englishwords.data.questions.QuestionWithUserInput

@RunWith(AndroidJUnit4::class)
class LearnSessionTests {
    private lateinit var wordDao: WordDao
    private lateinit var courseDao: CourseDao
    private lateinit var db: AppDatabase
    private lateinit var repository: WordsRepository
    private lateinit var session: LearnSession

    @Before
    @Throws(Exception::class)
    fun initialize() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.getInstance(context)
        delay(3000)
        wordDao = db.wordDao()
        courseDao = db.courseDao()
        repository = WordsRepository.getInstance(wordDao, courseDao)

        session = LearnSessionFactory.createDefaultSession(repository.getNewWords(
            LearnSessionFactory.WORDS_FOR_SESSION,
            1
        ),
            repository.getNewWords(LearnSessionFactory.TRANSLATIONS_FOR_SESSION, 1)
                .map { it.translations.random() })
    }

    @After
    @Throws(Exception::class)
    fun closeDB() = runBlocking {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun sizeCheck() {
        assert(session.questionsAndAnswers.keys.size == LearnSessionFactory.QUESTIONS_COUNT)
    }

    @Test
    @Throws(Exception::class)
    fun checkContent() {
        for (question in session.questionsAndAnswers.keys) {
            when (question) {
                is QuestionWithSelectableAnswer -> {
                    assert(question.answerVariants.size == 4)
                    var containsFlag = false
                    question.word.translations.forEach {
                        if (it.translation in question.answerVariants) containsFlag = true
                    }
                    assert(containsFlag)
                    assert(
                        question.getWordsWithErrors(Answer(listOf(question.word.translations.random())))
                            .isEmpty()
                    )
                    assert(
                        question.getWordsWithErrors(Answer(listOf("jfldkasjfkl;adsdk;sjaflk;a")))
                            .first() == question.word
                    )
                }
                is QuestionWithMatching -> {
                    assert(question.shuffledAnswers.size == 4)
                }
                is QuestionWithUserInput -> {
                    assert(
                        question.getWordsWithErrors(Answer(listOf(question.word.translations.random())))
                            .isEmpty()
                    )
                    assert(
                        question.getWordsWithErrors(Answer(listOf("jfldkasjfkl;adsdk;sjaflk;a")))
                            .first() == question.word
                    )
                }
            }
        }
    }

}