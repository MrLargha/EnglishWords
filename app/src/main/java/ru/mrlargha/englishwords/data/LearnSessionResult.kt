package ru.mrlargha.englishwords.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

typealias LSRWithAllDetails = LearnSessionResultWithDetails

@Entity(tableName = "sessions_results")
data class LearnSessionResult(
    val learnDate: Date,
    val relatedCourseID: Int,
    val successPercents: Int,
    val newWordsPercent: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

@Entity(tableName = "learn_session_details")
data class LearnSessionResultDetail(
    val relatedSessionResultId: Int,
    val relatedWordId: Long,
    val success: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

data class LearnSessionResultWithDetails(
    @Embedded val lsr: LearnSessionResult,
    @Relation(
        entity = LearnSessionResultDetail::class,
        parentColumn = "id",
        entityColumn = "relatedSessionResultId"
    )
    val details: List<LearnSessionResultDetailWithWordWithTranslation>
)

data class LearnSessionResultDetailWithWordWithTranslation(
    @Embedded val lsrDetails: LearnSessionResultDetail,
    @Relation(
        entity = Word::class,
        parentColumn = "relatedWordId",
        entityColumn = "wordId"
    )
    val word: WordWithTranslation
)