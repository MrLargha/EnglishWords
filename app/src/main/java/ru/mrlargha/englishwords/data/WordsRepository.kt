package ru.mrlargha.englishwords.data

class WordsRepository private constructor(
    private val wordDao: WordDao,
    private val courseDao: CourseDao
) {

    companion object {
        @Volatile
        private var instance: WordsRepository? = null

        @JvmStatic
        fun getInstance(wordDao: WordDao, courseDao: CourseDao) =
            instance ?: synchronized(this) {
                instance ?: WordsRepository(wordDao, courseDao).also { instance = it }
            }
    }

    suspend fun getNewWords(amount: Int, courseId: Int): List<WordWithTranslation> =
        wordDao.getNewRandomWordsWithTranslations(amount, courseId)


    suspend fun getWordsWithUserErrors(amount: Int, courseId: Int): List<WordWithTranslation> =
        wordDao.getRandomWordsWithTranslationsWithErrors(amount, courseId)


}