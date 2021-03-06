package ru.mrlargha.englishwords

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.mrlargha.englishwords.data.*
import java.io.IOException

// TODO Create tests for another DAO's methods

@RunWith(AndroidJUnit4::class)
class EntityRWTest {
    private lateinit var wordDao: WordDao
    private lateinit var learnSessionResultDao: LearnSessionResultDao
    private lateinit var db: AppDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        wordDao = db.wordDao()
        learnSessionResultDao = db.learnSessionResultDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() = runBlocking {
        wordDao.deleteAllTranslations()
        wordDao.deleteAllWords()
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun wordWithTranslationRW() = runBlocking {

        val generatedWords = emptyList<WordWithTranslation>().toMutableList()

        for (i in 1..10) {
            val word = WordWithTranslation(
                Word(i.toLong(), "room#$i", 1),
                listOf(
                    Translation(translatableWordId = i.toLong(), translationText = "комната#$i"),
                    Translation(translatableWordId = i.toLong(), translationText = "помещение#$i")
                )
            )
            generatedWords += word
            wordDao.insertWordWithTranslation(word)
        }

        val newWords = wordDao.getNewRandomWordsWithTranslations(10, 1)
        assert(generatedWords == newWords)
    }

    @Test
    @Throws(Exception::class)
    fun testLSRWithDetails() = runBlocking {
        // TODO("Implement this fucking test"
    }

}