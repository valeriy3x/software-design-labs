package by.bsuir.tabatatimer

import android.app.Application
import android.content.Context

class TabataTimerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        TabataTimerApplication.applicationContext = applicationContext
    }

    companion object {
        var applicationContext: Context? = null
        
    }
}