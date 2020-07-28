package ru.mrlargha.englishwords.data

class WordsRepository private constructor(val wordDao: WordDao, val courseDao: CourseDao) {

    companion object {
        @Volatile
        private var instance: WordsRepository? = null

        @JvmStatic
        fun getInstance(wordDao: WordDao, courseDao: CourseDao) =
            instance ?: synchronized(this) {
                instance ?: WordsRepository(wordDao, courseDao).also { instance = it }
            }
    }
}