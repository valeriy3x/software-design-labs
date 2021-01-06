package by.bsuir.tabatatimer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.room.Room
import by.bsuir.tabatatimer.database.AppDatabase
import by.bsuir.tabatatimer.utilities.LocaleHelper


class TabataTimerApplication : Application() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        TabataTimerApplication.applicationContext = applicationContext
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tabata-base")
            .fallbackToDestructiveMigration().build()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        loadSettings()
        createNotificationChannel()
    }

    companion object {
        var applicationContext: Context? = null
        lateinit var database: AppDatabase

        const val notificationChannelId = "timerNotification"
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            notificationChannelId,
            "Timer Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    private fun loadSettings() {
        val language =
            sharedPreferences.getString(applicationContext.getString(R.string.lang_key), "en")
        LocaleHelper.setLocale(applicationContext, language)

        val theme =
            sharedPreferences.getBoolean(applicationContext.getString(R.string.theme_key), false)
        if (theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


    }
}