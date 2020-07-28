package ru.mrlargha.englishwords.data

import androidx.room.*

@Dao
interface WordDao {
    @Transaction
    @Query("SELECT * FROM words WHERE words.courseId=:course ORDER BY RANDOM() LIMIT :amount")
    fun getRandomWordsWithTranslations(amount: Int, course: Int): List<WordWithTranslation>

    @Query("SELECT * FROM words")
    fun getAllWords(): List<Word>

    @Query("SELECT * FROM translations")
    fun getAllTranslations(): List<Translation>

    @Transaction
    @Insert
    fun insertWordWithTranslation(word: WordWithTranslation) {
        insertWord(word.word)
        word.translations.forEach(::insertTranslation)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTranslation(translation: Translation)

    @Query("DELETE FROM words")
    fun deleteAllWords()

    @Query("DELETE FROM translations")
    fun deleteAllTranslations()

}