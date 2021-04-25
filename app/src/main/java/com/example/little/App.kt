package com.example.little

import android.app.Application
import com.little.ext.setAppDelegate

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setAppDelegate(this)
    }

}