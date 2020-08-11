package ru.mrlargha.englishwords.data

class WordsRepository private constructor(
    private val wordDao: WordDao
) {

    companion object {
        @Volatile
        private var instance: WordsRepository? = null

        @JvmStatic
        fun getInstance(wordDao: WordDao) =
            instance ?: synchronized(this) {
                instance ?: WordsRepository(wordDao).also { instance = it }
            }
    }

    suspend fun getNewWords(amount: Int, courseId: Int): List<WordWithTranslation> =
        wordDao.getNewRandomWordsWithTranslations(amount, courseId)


    suspend fun getWordsWithUserErrors(amount: Int, courseId: Int): List<WordWithTranslation> =
        wordDao.getRandomWordsWithTranslationsWithErrors(amount, courseId)

    suspend fun getTranslations(amount: Int): List<Translation> = wordDao.getTranslations(amount)

}