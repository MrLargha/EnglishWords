package ru.mrlargha.englishwords.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import ru.mrlargha.englishwords.utility.DATABASE_NAME
import ru.mrlargha.englishwords.utility.DateConverter
import ru.mrlargha.englishwords.workers.WordsDatabaseWorker

@Database(
    entities = [Word::class, Translation::class, Course::class, LearnSessionResult::class,
        LearnSessionResultDetail::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun courseDao(): CourseDao
    abstract fun learnSessionResultDao(): LearnSessionResultDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        WorkManager.getInstance(context).enqueue(
                            OneTimeWorkRequestBuilder<WordsDatabaseWorker>().build()
                        )
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        WorkManager.getInstance(context).enqueue(
                            OneTimeWorkRequestBuilder<WordsDatabaseWorker>().build()
                        )
                    }
                }).build()
        }
    }
}