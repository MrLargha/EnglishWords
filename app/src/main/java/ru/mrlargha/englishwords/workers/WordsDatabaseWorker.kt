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
import ru.mrlargha.englishwords.data.WordWithTranslation
import ru.mrlargha.englishwords.utility.WORDS_ASSETS_FILENAME
import java.lang.Exception

class WordsDatabaseWorker(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            withContext(Dispatchers.IO) {
                applicationContext.assets.open(WORDS_ASSETS_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val wordType = object : TypeToken<List<WordWithTranslation>>() {}.type
                        val wordsList: List<WordWithTranslation> =
                            Gson().fromJson(jsonReader, wordType)

                        val database = AppDatabase.getInstance(applicationContext)

                        wordsList.forEach { database.wordDao().insertWordWithTranslation(it) }

                        Log.d(TAG, "Database populated!")

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