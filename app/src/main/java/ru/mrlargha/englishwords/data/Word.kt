package ru.mrlargha.englishwords.data

import androidx.room.*

@Entity(tableName = "words")
data class Word(
    @PrimaryKey val wordId: Long = 0,
    val englishWord: String,
    val courseId: Int
)

@Entity(
    tableName = "translations",
    indices = [Index(value = ["translatableWordId", "translation"], unique = true)]
)
data class Translation(
    @PrimaryKey(autoGenerate = true) val translationId: Long = 0,
    val translatableWordId: Long,
    val translation: String
)

data class WordWithTranslation(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "wordId",
        entityColumn = "translatableWordId"
    )
    val translations: List<Translation>
)