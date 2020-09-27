package ru.mrlargha.englishwords.data

class LearnSessionResultRepository(private val learnSessionResultDao: LearnSessionResultDao) {
    companion object {
        @Volatile
        private var instance: LearnSessionResultRepository? = null

        @JvmStatic
        fun getInstance(learnSessionResultDao: LearnSessionResultDao) =
            instance ?: synchronized(this) {
                instance ?: LearnSessionResultRepository(learnSessionResultDao).also {
                    instance = it
                }
            }
    }

    suspend fun insertResult(result: LearnSessionResult): Int =
        learnSessionResultDao.insertResult(result).toInt()

    suspend fun getResultByID(resultID: Int) =
        learnSessionResultDao.getResultsByID(resultID).first()

    suspend fun insertResultDetails(details: List<LearnSessionResultDetail>) =
        learnSessionResultDao.insertLearnSessionDetails(details)

    suspend fun getResultWithDetails(resultID: Int) =
        learnSessionResultDao.getLearnSessionResultsWithDetails(resultID).first()
}