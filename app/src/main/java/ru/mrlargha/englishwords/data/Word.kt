package ru.mrlargha.englishwords.data

import androidx.room.*
import com.google.gson.annotations.SerializedName
import ru.mrlargha.englishwords.utility.RIGHT_ANSWERS_IN_ROW_LIMIT

@Entity(tableName = "words")
data class Word(
    @PrimaryKey val wordId: Long = 0,
    val englishWord: String,
    val courseId: Int,
    var rightAnswersInRow: Int = 0,
    var wasShownToUser: Boolean = false
) {
    fun isLearned() = rightAnswersInRow > RIGHT_ANSWERS_IN_ROW_LIMIT
}

@Entity(
    tableName = "translations",
    indices = [Index(value = ["translatableWordId", "translationText"], unique = true)]
)
data class Translation(
    @PrimaryKey(autoGenerate = true) val translationId: Long = 0,
    val translatableWordId: Long,
    @SerializedName("translation") val translationText: String
)

data class WordWithTranslation(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "wordId",
        entityColumn = "translatableWordId"
    )
    val translations: List<Translation>
)