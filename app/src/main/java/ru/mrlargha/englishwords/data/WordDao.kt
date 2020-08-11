package ru.mrlargha.englishwords.data

import androidx.room.*
import ru.mrlargha.englishwords.utility.RIGHT_ANSWERS_IN_ROW_LIMIT

@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM words WHERE words.courseId=:course AND NOT words.wasShownToUser ORDER BY RANDOM() LIMIT :amount")
    suspend fun getNewRandomWordsWithTranslations(
        amount: Int,
        course: Int
    ): List<WordWithTranslation>

    @Transaction
    @Query("SELECT * FROM words WHERE words.courseId=:course AND words.wasShownToUser AND words.rightAnswersInRow < :rightAnswersInRow ORDER BY words.rightAnswersInRow ASC LIMIT :amount")
    suspend fun getRandomWordsWithTranslationsWithErrors(
        amount: Int,
        course: Int,
        rightAnswersInRow: Int = RIGHT_ANSWERS_IN_ROW_LIMIT
    ): List<WordWithTranslation>

    @Query("SELECT * FROM translations ORDER BY RANDOM() LIMIT :amount")
    suspend fun getTranslations(amount: Int): List<Translation>

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM translations")
    suspend fun getAllTranslations(): List<Translation>

    @Update
    suspend fun updateWords(vararg words: Word)

    @Transaction
    @Insert
    suspend fun insertWordWithTranslation(word: WordWithTranslation) {
        insertWord(word.word)
        insertTranslations(word.translations)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(word: List<Word>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translation: Translation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslations(translations: List<Translation>)

    @Query("DELETE FROM words")
    suspend fun deleteAllWords()

    @Query("DELETE FROM translations")
    suspend fun deleteAllTranslations()

    @Transaction
    suspend fun deleteAll() {
        deleteAllTranslations()
        deleteAllWords()
    }
}