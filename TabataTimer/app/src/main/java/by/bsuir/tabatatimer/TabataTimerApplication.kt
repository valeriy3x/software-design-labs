package by.bsuir.tabatatimer

import android.app.Application
import android.content.Context
import androidx.room.Room
import by.bsuir.tabatatimer.database.AppDatabase

class TabataTimerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        TabataTimerApplication.applicationContext = applicationContext
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tabata-base")
            .fallbackToDestructiveMigration().build()
    }

    companion object {
        var applicationContext: Context? = null
        lateinit var database: AppDatabase
    }
}