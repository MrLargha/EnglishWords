package ru.mrlargha.englishwords.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface LearnSessionResultDao {
    @Insert
    suspend fun insertResult(result: LearnSessionResult): Long

    @Insert
    suspend fun insertLearnSessionDetails(details: List<LearnSessionResultDetail>)

    @Query("SELECT * FROM sessions_results WHERE id=:resultID")
    suspend fun getResultsByID(resultID: Int): List<LearnSessionResult>

    @Transaction
    @Query("SELECT * FROM sessions_results WHERE id=:resultID")
    suspend fun getLearnSessionResultsWithDetails(resultID: Int):
            List<LSRWithAllDetails>
}
