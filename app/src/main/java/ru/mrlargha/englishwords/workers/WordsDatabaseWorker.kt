package ru.mrlargha.englishwords.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import ru.mrlargha.englishwords.data.AppDatabase
import ru.mrlargha.englishwords.data.Course
import ru.mrlargha.englishwords.data.WordWithTranslation
import ru.mrlargha.englishwords.utility.COURSES_ASSETS_FILENAME
import ru.mrlargha.englishwords.utility.WORDS_ASSETS_FILENAME

class WordsDatabaseWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            withContext(Dispatchers.IO) {
                val database = AppDatabase.getInstance(applicationContext)
                applicationContext.assets.open(WORDS_ASSETS_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val wordType = object : TypeToken<List<WordWithTranslation>>() {}.type
                        val wordsList: List<WordWithTranslation> =
                            Gson().fromJson(jsonReader, wordType)
                        wordsList.forEach { database.wordDao().insertWordWithTranslation(it) }

                        Log.d(TAG, "Words inserted!")
                        Result.success()
                    }
                }

                applicationContext.assets.open(COURSES_ASSETS_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val courseType = object : TypeToken<List<Course>>() {}.type
                        val coursesList: List<Course> =
                            Gson().fromJson(jsonReader, courseType)
                        database.courseDao().insertCourses(coursesList)

                        Log.d(TAG, "Courses inserted!")
                        Result.success()
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error populating database! ${ex.message}")
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "WordsDatabaseWorker"
    }

}