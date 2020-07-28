package ru.mrlargha.englishwords

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mrlargha.englishwords.data.AppDatabase
import ru.mrlargha.englishwords.data.Translation
import ru.mrlargha.englishwords.data.Word
import ru.mrlargha.englishwords.data.WordWithTranslation
import ru.mrlargha.englishwords.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.learnFragment,
                R.id.vocabularyFragment,
                R.id.statisticsFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        mBinding.navView.setupWithNavController(navController)

        lifecycleScope.launch {
            requestShit()
        }
    }

    private suspend fun requestShit(){
        val db = AppDatabase.getInstance(applicationContext)
        lateinit var words: List<WordWithTranslation>
        withContext(Dispatchers.IO) {
            words = db.wordDao().getRandomWordsWithTranslations(10, 4)
        }
        Log.d("TAG", "Fetched ${words.size} words")
    }
}