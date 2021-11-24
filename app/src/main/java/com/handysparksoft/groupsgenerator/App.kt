package com.handysparksoft.groupsgenerator

import android.app.Application
import com.handysparksoft.groupsgenerator.data.Prefs

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

    companion object {
        var prefs: Prefs? = null
    }
}
