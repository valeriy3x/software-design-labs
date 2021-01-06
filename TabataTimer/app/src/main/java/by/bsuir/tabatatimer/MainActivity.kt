package by.bsuir.tabatatimer

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import by.bsuir.tabatatimer.utilities.LocaleHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val language =
            sharedPreferences.getString(applicationContext.getString(R.string.lang_key), "en")
        val configLang: Configuration = resources.configuration
        configLang.setLocale(Locale(language))
        resources.updateConfiguration(configLang, resources.displayMetrics)
        super.onCreate(savedInstanceState)


        val sizeCoef = sharedPreferences.getFloat(this.getString(R.string.size_key), 1.15f)
        val configuration: Configuration = resources.configuration
        configuration.fontScale = sizeCoef

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)



        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment

        navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.settingsFragment)
        )

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when (destination.id) {
                R.id.addWorkoutFragment, R.id.workoutFragment, R.id.splashFragment -> {
                    bottomNav.visibility = View.GONE
                    toolbar.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                }
            }

        }

        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}