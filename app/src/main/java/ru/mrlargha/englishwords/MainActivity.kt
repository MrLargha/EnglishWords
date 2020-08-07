package ru.mrlargha.englishwords

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.mrlargha.englishwords.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.learnFragment,
                R.id.vocabularyFragment,
                R.id.statisticsFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        mBinding.navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }
}